package com.fati.conversionservice.infrastructure.exception;

import java.io.Serial;

/**
 * @author fati
 * @version 1.0
 * @since 18/Jun/2023
 */

public class ConversionPersistenceException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -891290840109628661L;

    public ConversionPersistenceException(String message) {
        super(message);
    }
}
