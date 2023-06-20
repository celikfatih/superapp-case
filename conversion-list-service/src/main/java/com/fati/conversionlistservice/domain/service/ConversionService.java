package com.fati.conversionlistservice.domain.service;

import com.fati.conversionlistservice.application.port.DataPersistencePort;
import com.fati.conversionlistservice.application.usecase.GetConversionListUseCase;
import com.fati.conversionlistservice.domain.model.Conversion;
import com.fati.conversionlistservice.domain.model.ConversionDto;
import com.fati.conversionlistservice.infrastructure.exception.RequestNotValidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * @author fati
 * @version 1.0
 * @since 19/Jun/2023
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ConversionService implements GetConversionListUseCase {

    private final DataPersistencePort dataPersistencePort;

    @Override
    public Page<ConversionDto> get(String transactionId, LocalDateTime transactionDate, Pageable pageable) {
        if (Objects.isNull(transactionId) && Objects.isNull(transactionDate)) {
            log.error("transactionId and transactionDate field empty!");
            throw new RequestNotValidException("At least one field (transactionId or transactionDate) must be provided!");
        }

        Page<Conversion> conversions = Optional.ofNullable(transactionId)
                .map(trxId -> {
                    log.info("Request contains transactionId and it is value: {}", transactionId);
                    return dataPersistencePort.getByTransactionId(trxId, pageable);
                })
                .orElseGet(() -> {
                    log.info("Request contains transactionDate and it is value: {}", transactionDate);
                    return dataPersistencePort.getByTransactionDateAfter(transactionDate, pageable);
                });

        return conversions.map(c -> new ConversionDto(c.getSource(), c.getSourceAmount(), c.getTarget(),
                c.getTargetAmount(), c.getRate(), c.getTransactionId(), c.getTransactionDate()));
    }
}
