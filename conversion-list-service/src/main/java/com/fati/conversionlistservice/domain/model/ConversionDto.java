package com.fati.conversionlistservice.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ConversionDto(String source, BigDecimal sourceAmount, String target, BigDecimal targetAmount,
                            BigDecimal rate, String transactionId, LocalDateTime transactionDate) {
}
