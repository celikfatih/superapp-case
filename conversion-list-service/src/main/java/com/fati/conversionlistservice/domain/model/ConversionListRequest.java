package com.fati.conversionlistservice.domain.model;

import java.time.LocalDateTime;

/**
 * @author fati
 * @version 1.0
 * @since 19/Jun/2023
 */

public record ConversionListRequest(String transactionId, LocalDateTime transactionDate) {
}
