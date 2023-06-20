package com.fati.exchangerateservice.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

/**
 * @author fati
 * @version 1.0
 * @since 18/Jun/2023
 */

@ResponseStatus(HttpStatus.CONFLICT)
public class ExternalApiInvocationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -3210267022434506811L;

    private static final String EXCEPTION_MESSAGE = "An unexpected problem occurred while using external api!";

    public ExternalApiInvocationException() {
        super(EXCEPTION_MESSAGE);
    }
}
