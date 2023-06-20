package com.fati.conversionservice.application.usecase;

import com.fati.conversionservice.domain.model.ConversionResponse;

import java.math.BigDecimal;

/**
 * @author fati
 * @version 1.0
 * @since 17/Jun/2023
 */
public interface ConvertAmountUseCase {

    ConversionResponse convert(String source, BigDecimal sourceAmount, String target);
}
