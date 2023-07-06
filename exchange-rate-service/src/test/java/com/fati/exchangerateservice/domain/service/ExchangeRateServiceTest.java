package com.fati.exchangerateservice.domain.service;

import com.fati.exchangerateservice.application.port.ExternalApiInvocationPort;
import com.fati.exchangerateservice.domain.model.ExchangeRateResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceTest {

    @InjectMocks
    private ExchangeRateService exchangeRateService;

    @Mock
    private ExternalApiInvocationPort externalApiInvocationPort;

    @Test
    void testWhenValidFromAndToShouldReturnExchangeRate() {
        String from = "EUR";
        String to = "USD";
        ExchangeRateResponse rate = new ExchangeRateResponse(BigDecimal.ONE);

        when(externalApiInvocationPort.invoke(from, to)).thenReturn(rate);

        ExchangeRateResponse actual = exchangeRateService.get(from, to);

        assertEquals(BigDecimal.ONE, actual.rate());
    }
}