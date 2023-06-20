package com.fati.conversionlistservice.infrastructure.configuration;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author fati
 * @version 1.0
 * @since 18/Jun/2023
 */

@Configuration
public class ValidationConfiguration {

    @Bean
    public ErrorAttributes validationErrorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
                Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);

                Throwable error = getError(webRequest);

                if (error instanceof MethodArgumentNotValidException ex) {
                    errorAttributes.put("message", ex.getBindingResult()
                            .getFieldErrors()
                            .stream()
                            .map(FieldError::getDefaultMessage)
                            .collect(Collectors.toSet()));
                }
                return errorAttributes;
            }
        };
    }
}
