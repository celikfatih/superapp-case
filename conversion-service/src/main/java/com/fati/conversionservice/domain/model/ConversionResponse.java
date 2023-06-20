package com.fati.conversionservice.domain.model;

import lombok.Builder;

import java.math.BigDecimal;

/**
 * @author fati
 * @version 1.0
 * @since 19/Jun/2023
 */

@Builder
public record ConversionResponse(BigDecimal targetAmount, String transactionId) {
}
