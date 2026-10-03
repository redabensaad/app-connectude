package com.redabensaad.connectude.infra.exceptions;

public class NotaNotFoundException extends RuntimeException {

    public NotaNotFoundException(String message) {
        super(message);
    }
}
