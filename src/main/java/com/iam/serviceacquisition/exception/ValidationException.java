package com.iam.serviceacquisition.exception;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

    private final Integer errorCode;

    public ValidationException(String msg, Integer errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }
}
