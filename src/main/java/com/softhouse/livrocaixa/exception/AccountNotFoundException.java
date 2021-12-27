package com.softhouse.livrocaixa.exception;

public class AccountNotFoundException extends EntityNotFoundException {

    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(Long id) {
        this(String.format("There is no account with id %d", id));
    }

}
