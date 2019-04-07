package com.bank.transaction.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class Transaction {

    public enum Type {
        CREDIT, DEBIT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Type type;

    @NotNull
    private Long accountId;

    @NotNull
    private Instant occurredAt;
}
