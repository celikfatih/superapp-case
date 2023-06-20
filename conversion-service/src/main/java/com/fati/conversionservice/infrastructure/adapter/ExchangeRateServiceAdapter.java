package com.fati.conversionservice.infrastructure.adapter;

import com.fati.conversionservice.application.port.InternalApiInvocationPort;
import com.fati.conversionservice.domain.model.ExchangeRateRequest;
import com.fati.conversionservice.domain.model.ExchangeRateResponse;
import com.fati.conversionservice.infrastructure.client.ExchangeRateServiceClient;
import com.fati.conversionservice.infrastructure.exception.InternalApiCallException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author fati
 * @version 1.0
 * @since 17/Jun/2023
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateServiceAdapter implements InternalApiInvocationPort {

    private final ExchangeRateServiceClient exchangeRateServiceClient;

    @Override
    public ExchangeRateResponse invoke(String from, String to) {
        try {
            ResponseEntity<ExchangeRateResponse> response = exchangeRateServiceClient
                    .getExchangeRate(ExchangeRateRequest.builder().from(from).to(to).build());

            if (log.isDebugEnabled()) {
                log.debug("Internal api invocation status [{}] and response is [{}]", response.getStatusCode(),
                        response.getBody());
            }

            if (response.getStatusCode().isError()) {
                log.error("Response status code is {}", response.getStatusCode());
                throw new InternalApiCallException("Internal api invocation failed!");
            }

            return response.getBody();
        } catch (Exception e) {
            log.error("Internal api invocation failed due to {}", e.getMessage());
            throw new InternalApiCallException(e.getMessage());
        }
    }
}
