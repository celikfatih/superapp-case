package com.fati.exchangerateservice.infrastructure.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.annotation.UserConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class CurrencyApiConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withPropertyValues("currency.base.url:https://test.com", "currency.api.key:apiKey")
            .withConfiguration(UserConfigurations.of(CurrencyApiConfiguration.class));

    @Test
    void currencyApiWebClient() {
        contextRunner.run(ctx -> assertThat(ctx).hasSingleBean(CurrencyApiConfiguration.class));
    }
}