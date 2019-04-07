package com.bank.transaction.controller;

import com.bank.transaction.event.TransactionInitiated;
import com.bank.transaction.model.Transaction;
import com.bank.transaction.repository.TransactionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping({"/api/v1/"})
public class TransactionController {

    private TransactionRepository transactionRepository;

    private KafkaTemplate<String, TransactionInitiated> kafkaTemplate;

    public TransactionController(TransactionRepository transactionRepository, KafkaTemplate<String, TransactionInitiated> kafkaTemplate) {
        this.transactionRepository = transactionRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/transactions")
    public List transactions(@RequestParam(required = false) Long accountId) {
        if (StringUtils.isEmpty(accountId)) {
            return transactionRepository.findAll();
        } else {
            return transactionRepository.findByAccount(accountId);
        }
    }

    @PostMapping("/transactions")
    public ResponseEntity<Transaction> create(@RequestBody @Valid TransactionRequest request) {
        Transaction transaction = transactionRepository.save(Transaction.builder()
                .accountId(request.getAccountId())
                .amount(request.getAmount())
                .type(request.getType())
                .occurredAt(Instant.now())
                .build());

        kafkaTemplate.send("account-events", TransactionInitiated.builder()
                .accountId(request.getAccountId())
                .amount(request.getAmount())
                .build());

        return ResponseEntity.ok(transaction);
    }
}
