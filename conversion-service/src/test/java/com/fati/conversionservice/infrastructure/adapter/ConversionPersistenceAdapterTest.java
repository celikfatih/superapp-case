package com.fati.conversionservice.infrastructure.adapter;

import com.fati.conversionservice.domain.model.Conversion;
import com.fati.conversionservice.infrastructure.exception.ConversionPersistenceException;
import com.fati.conversionservice.infrastructure.repository.ConversionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConversionPersistenceAdapterTest {

    @InjectMocks
    private ConversionPersistenceAdapter conversionPersistenceAdapter;

    @Mock
    private ConversionRepository conversionRepository;

    @Test
    void testCreateWhenValidInputsVerifyRepositorySaveCall() {
        String source = "TRY";
        String target = "USD";
        BigDecimal sourceAmount = BigDecimal.TEN;
        BigDecimal targetAmount = BigDecimal.ONE;
        BigDecimal rate = BigDecimal.TEN;

        when(conversionRepository.save(any(Conversion.class))).thenReturn(Conversion.builder().build());

        conversionPersistenceAdapter.create(source, sourceAmount, target, targetAmount, rate);

        verify(conversionRepository, times(1))
                .save(argThat(c -> c.getTargetAmount().equals(BigDecimal.ONE)));
    }

    @Test
    void testCreateWhenNotValidInputShouldThrowConversionPersistenceException() {
        String source = "TRY";
        String target = "USD";
        BigDecimal targetAmount = BigDecimal.ONE;
        BigDecimal rate = BigDecimal.TEN;

        when(conversionRepository.save(any(Conversion.class))).thenThrow(new RuntimeException("test"));

        assertThrows(ConversionPersistenceException.class,
                () -> conversionPersistenceAdapter.create(source, null, target, targetAmount, rate));
    }
}