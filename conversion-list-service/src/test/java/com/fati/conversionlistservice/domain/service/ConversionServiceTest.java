package com.fati.conversionlistservice.domain.service;

import com.fati.conversionlistservice.application.port.DataPersistencePort;
import com.fati.conversionlistservice.domain.model.Conversion;
import com.fati.conversionlistservice.domain.model.ConversionDto;
import com.fati.conversionlistservice.infrastructure.exception.RequestNotValidException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConversionServiceTest {

    @InjectMocks
    private ConversionService conversionService;

    @Mock
    private DataPersistencePort dataPersistencePort;

    @Test
    void testGetWhenTransactionIdAndTransactionDateNullShouldThrowRequestNotValidException() {
        PageRequest page = PageRequest.of(0, 1);

        assertThrows(RequestNotValidException.class, () -> conversionService.get(null, null, page));
    }

    @Test
    void testGetWhenValidTransactionIdShouldReturnExistConversionByTransactionId() {
        String transactionId = "123456";
        BigDecimal targetAmount = BigDecimal.TEN;
        PageRequest page = PageRequest.of(0, 1);
        Conversion conversion = Conversion.builder().transactionId(transactionId).targetAmount(targetAmount).build();

        when(dataPersistencePort.getByTransactionId(transactionId, page))
                .thenReturn(new PageImpl<>(Collections.singletonList(conversion)));

        Page<ConversionDto> actual = conversionService.get(transactionId, null, page);

        assertFalse(actual.isEmpty());
        assertEquals(targetAmount, actual.getContent().get(0).targetAmount());
    }

    @Test
    void testGetWhenValidTransactionDateShouldReturnExistConversionsAfterTransactionDate() {
        LocalDateTime date = LocalDateTime.now();
        PageRequest page = PageRequest.of(0, 1);
        BigDecimal euro = BigDecimal.TEN;
        BigDecimal usd = BigDecimal.ONE;
        Conversion euroConversion = Conversion.builder().transactionId("EUR1234").targetAmount(euro).build();
        Conversion usdConversion = Conversion.builder().transactionId("USD1234").targetAmount(usd).build();
        List<Conversion> conversions = Arrays.asList(usdConversion, euroConversion);
        when(dataPersistencePort.getByTransactionDateAfter(date, page))
                .thenReturn(new PageImpl<>(conversions));

        Page<ConversionDto> actual = conversionService.get(null, date, page);

        assertEquals(conversions.size(), actual.getTotalElements());
        assertEquals(usd, actual.getContent().get(0).targetAmount());
        assertEquals(euro, actual.getContent().get(1).targetAmount());
    }
}