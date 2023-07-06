package com.fati.exchangerateservice.infrastructure.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.context.annotation.UserConfigurations;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class OpenApiConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(MockConfiguration.class))
            .withConfiguration(UserConfigurations.of(OpenApiConfiguration.class));

    @Test
    void conversionApi() {
        contextRunner.run(ctx -> assertThat(ctx).hasSingleBean(OpenApiConfiguration.class));
    }

    @Configuration
    public static class MockConfiguration {

        @Bean
        public BuildProperties buildProperties() {
            Properties properties = new Properties();
            properties.setProperty("version", "0.0.1");
            properties.setProperty("artifact", "conversion-service");
            return new BuildProperties(properties);
        }
    }
}