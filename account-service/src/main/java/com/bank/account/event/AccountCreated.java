package com.bank.account.event;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

@Data
@Builder
public class AccountCreated {
    @Default
    private String eventType = "account-created";

    private Long amount;

    private Long accountId;
}
