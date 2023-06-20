package com.fati.conversionservice.infrastructure.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValidationConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(ValidationConfiguration.class));

    @Test
    void testValidationErrorAttributes() {
        contextRunner.run(ctx -> {
            DefaultErrorAttributes errorAttributes = ctx.getBean(DefaultErrorAttributes.class);
            WebRequest webRequest = mock(WebRequest.class);
            ErrorAttributeOptions defaults = ErrorAttributeOptions.defaults();

            when(webRequest.getAttribute("org.springframework.boot.web.servlet.error.DefaultErrorAttributes.ERROR",
                    RequestAttributes.SCOPE_REQUEST))
                    .thenReturn(new MethodArgumentNotValidException(
                            new MethodParameter(ValidationConfiguration.class.getMethods()[0], -1),
                            new BindException(new Object(), "")));
            errorAttributes.getErrorAttributes(webRequest, defaults);

            assertThat(ctx).hasSingleBean(ErrorAttributes.class);
        });
    }
}