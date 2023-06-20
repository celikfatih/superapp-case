package com.fati.conversionservice.infrastructure.adapter;

import com.fati.conversionservice.domain.model.ExchangeRateRequest;
import com.fati.conversionservice.domain.model.ExchangeRateResponse;
import com.fati.conversionservice.infrastructure.client.ExchangeRateServiceClient;
import com.fati.conversionservice.infrastructure.exception.InternalApiCallException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceAdapterTest {

    @InjectMocks
    private ExchangeRateServiceAdapter exchangeRateServiceAdapter;

    @Mock
    private ExchangeRateServiceClient exchangeRateServiceClient;

    @Test
    void testInvokeWhenValidInputsShouldReturnExchangeRate() {
        String from = "USD";
        String to = "TRY";
        ExchangeRateResponse response = new ExchangeRateResponse(BigDecimal.ONE);

        when(exchangeRateServiceClient.getExchangeRate(any(ExchangeRateRequest.class)))
                .thenReturn(ResponseEntity.ok(response));

        ExchangeRateResponse actual = exchangeRateServiceAdapter.invoke(from, to);

        assertEquals(BigDecimal.ONE, actual.rate());
    }

    @Test
    void testInvokeWhenApiCallFailedShouldThrowInternalApiCallException() {
        String from = "USD";

        when(exchangeRateServiceClient.getExchangeRate(any(ExchangeRateRequest.class)))
                .thenThrow(InternalApiCallException.class);

        assertThrows(InternalApiCallException.class, () -> exchangeRateServiceAdapter.invoke(from, null));
    }

    @Test
    void testInvokeWhenApiResponseErrorStatusShouldThrowInternalApiCallException() {
        String from = "USD";
        String to = "TRY";

        when(exchangeRateServiceClient.getExchangeRate(any(ExchangeRateRequest.class)))
                .thenReturn(ResponseEntity.internalServerError().build());

        assertThrows(InternalApiCallException.class, () -> exchangeRateServiceAdapter.invoke(from, null));
    }
}