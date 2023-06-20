package com.fati.exchangerateservice.application.usecase;

import com.fati.exchangerateservice.domain.model.ExchangeRateResponse;

/**
 * @author fati
 * @version 1.0
 * @since 17/Jun/2023
 */
public interface GetExchangeRateUseCase {
    ExchangeRateResponse get(String from, String to);
}
