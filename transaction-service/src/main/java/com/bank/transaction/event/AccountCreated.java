package com.bank.transaction.event;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountCreated {
    private String eventType;

    private Long amount;

    private Long accountId;
}
