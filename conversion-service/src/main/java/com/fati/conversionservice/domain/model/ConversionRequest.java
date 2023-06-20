package com.fati.conversionservice.domain.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ConversionRequest(@NotBlank(message = "\"source\" field is must be not empty!") String source,
                                @NotBlank(message = "\"target\" field is must be not empty!") String target,
                                @DecimalMin(value = "0.0", inclusive = false,
                                        message = "\"sourceAmount\" must be greater than zero!")
                                BigDecimal sourceAmount) {
}
