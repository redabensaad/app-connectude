package com.redabensaad.connectude.infra.exceptions;

public class CursoNotFoundException extends RuntimeException {

    public CursoNotFoundException(String message) {
        super(message);
    }
}
