package com.fati.conversionlistservice.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

/**
 * @author fati
 * @version 1.0
 * @since 19/Jun/2023
 */

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequestNotValidException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -8324562484589879751L;

    public RequestNotValidException(String message) {
        super(message);
    }
}
