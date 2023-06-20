package com.fati.conversionlistservice.infrastructure.adapter;

import com.fati.conversionlistservice.application.port.DataPersistencePort;
import com.fati.conversionlistservice.domain.model.Conversion;
import com.fati.conversionlistservice.infrastructure.repository.ConversionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author fati
 * @version 1.0
 * @since 19/Jun/2023
 */

@Component
@RequiredArgsConstructor
public class ConversionPersistenceAdapter implements DataPersistencePort {

    private final ConversionRepository conversionRepository;

    @Override
    public Page<Conversion> getByTransactionId(String transactionId, Pageable pageable) {
        return conversionRepository.getAllByTransactionId(transactionId, pageable);
    }

    @Override
    public Page<Conversion> getByTransactionDateAfter(LocalDateTime transactionDate, Pageable pageable) {
        return conversionRepository.getAllByTransactionDateAfter(transactionDate, pageable);
    }
}
