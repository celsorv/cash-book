package com.softhouse.livrocaixa.exception;

public class TransactionNotFoundException extends EntityNotFoundException {

    public TransactionNotFoundException(String message) {
        super(message);
    }

    public TransactionNotFoundException(Long id) {
        this(String.format("There is no transaction with id %d", id));
    }

}
