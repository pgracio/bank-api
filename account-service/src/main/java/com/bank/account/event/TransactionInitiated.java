package com.bank.account.event;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class TransactionInitiated {

    public enum Type {
        CREDIT, DEBIT
    }

    private String eventType;

    private Instant occurredAt;

    private Long accountId;

    private Long amount;

    private Type type;
}
