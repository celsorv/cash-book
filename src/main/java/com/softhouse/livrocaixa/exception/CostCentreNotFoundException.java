package com.softhouse.livrocaixa.exception;

public class CostCentreNotFoundException extends EntityNotFoundException {

    public CostCentreNotFoundException(String message) {
        super(message);
    }

    public CostCentreNotFoundException(Long id) {
        this(String.format("There is no cost centre with id %d", id));
    }

}
