package com.bank.transaction.controller;

import com.bank.transaction.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;


@AllArgsConstructor
@NoArgsConstructor
@Data
class TransactionRequest {
    @NotNull
    private Long accountId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Transaction.Type type;

    @NotNull
    private Long amount;
}
