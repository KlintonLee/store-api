package com.klinton.store.domain.exception;

public class DomainException extends NoStacktraceException {

    public DomainException(String message) {
        super(message);
    }
}
