package com.softhouse.livrocaixa.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {

    DEBIT("Debit"),
    CREDIT("Credit");

    private final String description;

}
