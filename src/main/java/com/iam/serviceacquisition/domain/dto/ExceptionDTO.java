package com.iam.serviceacquisition.domain.dto;

import lombok.Getter;

@Getter
public class ExceptionDTO {

    private final Integer code;
	private final String message;

    public ExceptionDTO(Integer errorCode, String message) {
        this.code = errorCode;
        this.message = message;
    }
}

