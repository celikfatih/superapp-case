package com.fati.conversionservice.application.port;

import com.fati.conversionservice.domain.model.ExchangeRateResponse;

/**
 * @author fati
 * @version 1.0
 * @since 17/Jun/2023
 */
public interface InternalApiInvocationPort {

    ExchangeRateResponse invoke(String from, String to);
}
