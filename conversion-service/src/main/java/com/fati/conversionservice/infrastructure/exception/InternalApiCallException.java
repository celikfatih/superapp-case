package com.fati.conversionservice.infrastructure.exception;

import java.io.Serial;

/**
 * @author fati
 * @version 1.0
 * @since 17/Jun/2023
 */

public class InternalApiCallException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -8742979715292770702L;

    public InternalApiCallException(String message) {
        super(message);
    }
}
