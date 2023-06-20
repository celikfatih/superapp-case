package com.fati.exchangerateservice.infrastructure.adapter;

import com.fati.exchangerateservice.application.port.ExternalApiInvocationPort;
import com.fati.exchangerateservice.domain.model.ExchangeRateResponse;
import com.fati.exchangerateservice.infrastructure.exception.ExternalApiInvocationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Optional;

import static com.fati.exchangerateservice.Constants.SOURCE_CURRENCY_FIELD_NAME;
import static com.fati.exchangerateservice.Constants.TARGET_CURRENCY_FIELD_NAME;

/**
 * @author fati
 * @version 1.0
 * @since 17/Jun/2023
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class CurrencyApiInvocationAdapter implements ExternalApiInvocationPort {

    private final WebClient webClient;

    @Override
    public ExchangeRateResponse invoke(String from, String to) {
        try {
            CurrencyApiInvocationAdapter.ExternalApiResponse response = webClient.get()
                    .uri(ub -> ub
                            .queryParam(SOURCE_CURRENCY_FIELD_NAME, from)
                            .queryParam(TARGET_CURRENCY_FIELD_NAME, to)
                            .build())
                    .retrieve()
                    .bodyToMono(CurrencyApiInvocationAdapter.ExternalApiResponse.class)
                    .block();

            if (log.isDebugEnabled()) {
                log.debug("External api invocation response is [{}]", response);
            }

            return Optional.ofNullable(response)
                    .map(ExternalApiResponse::getData)
                    .map(e -> e.get(to))
                    .map(ExternalApiResponse.ExchangeValue::getValue)
                    .map(ExchangeRateResponse::new)
                    .orElseThrow(ExternalApiInvocationException::new);
        } catch (Exception e) {
            log.error("An unexpected error occurred while invoking the api and message is: {}", e.getMessage());
            throw new ExternalApiInvocationException();
        }
    }

    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class ExternalApiResponse {
        private LinkedHashMap<String, ExchangeValue> data;

        @Getter
        @ToString
        @NoArgsConstructor
        @AllArgsConstructor
        public static final class ExchangeValue {
            private String code;
            private BigDecimal value;
        }
    }
}
