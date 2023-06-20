package com.fati.conversionservice.infrastructure.client;

import com.fati.conversionservice.domain.model.ExchangeRateRequest;
import com.fati.conversionservice.domain.model.ExchangeRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author fati
 * @version 1.0
 * @since 17/Jun/2023
 */

@FeignClient(
        value = "exchangeServiceClient",
        url = "${exchange.service.client}"
)
public interface ExchangeRateServiceClient {

    @PostMapping(path = "/api/v1/rates/get-exchange-rate")
    ResponseEntity<ExchangeRateResponse> getExchangeRate(@RequestBody ExchangeRateRequest request);
}
