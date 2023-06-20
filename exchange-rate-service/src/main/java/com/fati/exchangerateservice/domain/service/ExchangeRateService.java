package com.fati.exchangerateservice.domain.service;

import com.fati.exchangerateservice.application.port.ExternalApiInvocationPort;
import com.fati.exchangerateservice.application.usecase.GetExchangeRateUseCase;
import com.fati.exchangerateservice.domain.model.ExchangeRateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author fati
 * @version 1.0
 * @since 17/Jun/2023
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateService implements GetExchangeRateUseCase {

    private final ExternalApiInvocationPort externalApiInvocationPort;

    @Override
    public ExchangeRateResponse get(String from, String to) {
        return externalApiInvocationPort.invoke(from, to);
    }
}
