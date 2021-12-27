package com.softhouse.livrocaixa.exception;

public class SubaccountNotFoundException extends EntityNotFoundException {

    public SubaccountNotFoundException(String message) {
        super(message);
    }

    public SubaccountNotFoundException(Long id) {
        this(String.format("There is no subaccount with id %d", id));
    }

}
