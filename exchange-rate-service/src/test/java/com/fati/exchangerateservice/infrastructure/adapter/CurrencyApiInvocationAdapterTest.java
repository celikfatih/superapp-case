package com.fati.exchangerateservice.infrastructure.adapter;

import com.fati.exchangerateservice.domain.model.ExchangeRateResponse;
import com.fati.exchangerateservice.infrastructure.exception.ExternalApiInvocationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyApiInvocationAdapterTest {

    @InjectMocks
    private CurrencyApiInvocationAdapter currencyApiInvocationAdapter;

    @Mock
    private WebClient webClient;

    @Test
    void testInvokeWhenExternalApiCallSuccessfulShouldReturnRateValue() {
        String from = "EUR";
        String to = "USD";
        LinkedHashMap<String, CurrencyApiInvocationAdapter.ExternalApiResponse.ExchangeValue> map = new LinkedHashMap<>();
        map.put(to, new CurrencyApiInvocationAdapter.ExternalApiResponse.ExchangeValue(to, BigDecimal.TEN));
        CurrencyApiInvocationAdapter.ExternalApiResponse response =
                new CurrencyApiInvocationAdapter.ExternalApiResponse(map);

        getMockWebClient(response);

        ExchangeRateResponse actual = currencyApiInvocationAdapter.invoke(from, to);

        assertEquals(BigDecimal.TEN, actual.rate());
    }

    @Test
    void testInvokeWhenExternalApiCallFailShouldReturnExternalApiInvocationException() {
        String from = "EUR";
        String to = "XTR";

        when(webClient.get()).thenThrow(new RuntimeException("test"));

        assertThrows(ExternalApiInvocationException.class, () -> currencyApiInvocationAdapter.invoke(from, to));
    }

    private void getMockWebClient(final CurrencyApiInvocationAdapter.ExternalApiResponse res) {
        final var uriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        final var headersSpec = mock(WebClient.RequestHeadersSpec.class);
        final var responseSpec = mock(WebClient.ResponseSpec.class);

        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(any(Function.class))).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec
                .bodyToMono(ArgumentMatchers.<Class<CurrencyApiInvocationAdapter.ExternalApiResponse>>notNull()))
                .thenReturn(Mono.just(res));
    }
}