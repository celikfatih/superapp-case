package com.fati.exchangerateservice.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author fati
 * @version 1.0
 * @since 20/Jun/2023
 */

@Configuration
public class CurrencyApiConfiguration {

    @Value("${currency.base.url:}")
    private String baseUrl;

    @Value("${currency.api.key:}")
    private String apiKey;

    @Bean
    public WebClient currencyApiWebClient() {
        return WebClient.builder()
                .baseUrl(new StringBuilder(baseUrl).append("?apikey=").append(apiKey).toString())
                .build();
    }
}
