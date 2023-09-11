package com.iam.serviceacquisition.exception.handler;


import com.iam.serviceacquisition.domain.dto.ExceptionDTO;
import com.iam.serviceacquisition.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleHttpException(ValidationException ex) {
        ExceptionDTO exceptionResponse = new ExceptionDTO(ex.getErrorCode(), ex.getMessage());
        log.error("ValidationException ", ex);
        return ResponseEntity.status(HttpStatus.valueOf(ex.getErrorCode())).body(exceptionResponse);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                               HttpStatusCode status, WebRequest request) {
        log.error("MethodArgumentNotValid ", ex);

        final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> errors = new ArrayList<>();
        fieldErrors.forEach(f -> errors.add(String.format("%s : %s", f.getField(), f.getDefaultMessage())));

        ExceptionDTO exceptionResponse = new ExceptionDTO(HttpStatus.BAD_REQUEST.value(),
                String.join(", ", errors));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
}
