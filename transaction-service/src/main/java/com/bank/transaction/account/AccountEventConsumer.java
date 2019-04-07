package com.bank.transaction.account;

import com.bank.transaction.event.AccountCreated;
import com.bank.transaction.model.Transaction;
import com.bank.transaction.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Slf4j
public class AccountEventConsumer {
    private TransactionRepository transactionRepository;

    public AccountEventConsumer(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @KafkaListener(topics = "account-events")
    public void listen(@Payload AccountCreated accountCreated) {
        log.info("Consumed  {}", accountCreated.toString());

        transactionRepository.save(Transaction.builder()
                .amount(accountCreated.getAmount())
                .accountId(accountCreated.getAccountId())
                .type(Transaction.Type.CREDIT)
                .occurredAt(Instant.now())
                .build());
    }
}
