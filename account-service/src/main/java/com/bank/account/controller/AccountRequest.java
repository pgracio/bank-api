package com.bank.account.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
class AccountRequest {
    @NotNull
    private Long customerId;
    @NotNull
    private Long initialBalance;
}
