package com.bank.account.transaction;

import com.bank.account.event.TransactionInitiated;
import com.bank.account.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TransactionEventConsumer {
    private AccountRepository accountRepository;

    public TransactionEventConsumer(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @KafkaListener(topics = "transaction-events")
    public void listen(@Payload TransactionInitiated transactionInitiated) {
        log.info("Consumed  {}", transactionInitiated.toString());
    }
}
