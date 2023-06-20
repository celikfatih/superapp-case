package com.fati.exchangerateservice.application.port;

import com.fati.exchangerateservice.domain.model.ExchangeRateResponse;

/**
 * @author fati
 * @version 1.0
 * @since 17/Jun/2023
 */
public interface ExternalApiInvocationPort {
    ExchangeRateResponse invoke(String from, String to);
}
