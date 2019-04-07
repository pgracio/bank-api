package com.bank.transaction.event;

import com.bank.transaction.model.Transaction;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class TransactionInitiated {

    @Builder.Default
    private String eventType = "account-initiated";

    private Instant occurredAt;

    private Long accountId;

    private Long amount;

    private Transaction.Type type;
}
