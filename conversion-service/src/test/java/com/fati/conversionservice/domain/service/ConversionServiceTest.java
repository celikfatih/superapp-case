package com.fati.conversionservice.domain.service;

import com.fati.conversionservice.application.port.DataPersistencePort;
import com.fati.conversionservice.application.port.InternalApiInvocationPort;
import com.fati.conversionservice.domain.model.ConversionResponse;
import com.fati.conversionservice.domain.model.ExchangeRateResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConversionServiceTest {

    @InjectMocks
    private ConversionService conversionService;

    @Mock
    private InternalApiInvocationPort internalApiInvocationPort;

    @Mock
    private DataPersistencePort dataPersistencePort;

    @Captor
    private ArgumentCaptor<BigDecimal> targetAmountCaptor;

    @Test
    void testConvertWhenValidInputsShouldReturnConversionResponse() {
        String source = "TRY";
        String target = "USD";
        BigDecimal sourceAmount = BigDecimal.TEN;
        BigDecimal rate = BigDecimal.TEN;

        when(internalApiInvocationPort.invoke(source, target)).thenReturn(new ExchangeRateResponse(rate));
        doNothing().when(dataPersistencePort).create(eq(source), eq(sourceAmount), eq(target),
                targetAmountCaptor.capture(), eq(rate));

        ConversionResponse actual = conversionService.convert(source, sourceAmount, target);

        assertEquals(targetAmountCaptor.getValue(), actual.targetAmount());
    }
}