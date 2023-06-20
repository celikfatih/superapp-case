package com.fati.exchangerateservice.infrastructure.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OpenApiConfiguration {

    private final BuildProperties buildProperties;

    @Bean
    public OpenAPI conversionApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Exchange Rate Api")
                        .version(buildProperties.getVersion())
                        .description(new StringBuilder(buildProperties.getArtifact()).append(" | ")
                                .append("Returns the requested exchange rate information.").toString())
                        .contact(new Contact().name("Me").email("celikfatih@protonmail.com"))
                        .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}