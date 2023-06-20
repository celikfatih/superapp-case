package com.fati.conversionlistservice.infrastructure.adapter;

import com.fati.conversionlistservice.domain.model.Conversion;
import com.fati.conversionlistservice.infrastructure.repository.ConversionRepository;
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

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConversionPersistenceAdapterTest {

    @InjectMocks
    private ConversionPersistenceAdapter conversionPersistenceAdapter;

    @Mock
    private ConversionRepository conversionRepository;

    @Test
    void testGetByTransactionId() {
        String transactionId = "123456";
        PageRequest page = PageRequest.of(0, 1);
        Conversion conversion = Conversion.builder().id(1L).transactionId(transactionId)
                .targetAmount(BigDecimal.ONE).build();

        when(conversionRepository.getAllByTransactionId(transactionId, page))
                .thenReturn(new PageImpl<>(Collections.singletonList(conversion)));

        Page<Conversion> actual = conversionPersistenceAdapter.getByTransactionId(transactionId, page);

        assertEquals(transactionId, actual.getContent().get(0).getTransactionId());
    }

    @Test
    void testGetByTransactionDateAfter() {
        LocalDateTime now = LocalDateTime.now();
        PageRequest page = PageRequest.of(0, 2);
        Conversion usdConversion = Conversion.builder().transactionDate(now).targetAmount(BigDecimal.ONE).build();
        Conversion euroConversion = Conversion.builder().transactionDate(now).targetAmount(BigDecimal.TEN).build();

        when(conversionRepository.getAllByTransactionDateAfter(now, page))
                .thenReturn(new PageImpl<>(Arrays.asList(usdConversion, euroConversion)));

        Page<Conversion> actual = conversionPersistenceAdapter.getByTransactionDateAfter(now, page);

        actual.getContent().forEach(c -> assertEquals(now, c.getTransactionDate()));
    }
}