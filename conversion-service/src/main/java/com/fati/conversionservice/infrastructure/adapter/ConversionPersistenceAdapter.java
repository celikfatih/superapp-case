package com.fati.conversionservice.infrastructure.adapter;

import com.fati.conversionservice.Constants;
import com.fati.conversionservice.application.port.DataPersistencePort;
import com.fati.conversionservice.domain.model.Conversion;
import com.fati.conversionservice.infrastructure.exception.ConversionPersistenceException;
import com.fati.conversionservice.infrastructure.repository.ConversionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author fati
 * @version 1.0
 * @since 17/Jun/2023
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class ConversionPersistenceAdapter implements DataPersistencePort {

    private final ConversionRepository conversionRepository;

    @Override
    public void create(String source, BigDecimal sourceAmount, String target, BigDecimal targetAmount,
                       BigDecimal rate) {
        try {
            String transactionId = MDC.get(Constants.TRX_ID_LOG_NAME);

            Conversion conversion = Conversion.builder()
                    .source(source)
                    .sourceAmount(sourceAmount)
                    .target(target)
                    .targetAmount(targetAmount)
                    .rate(rate)
                    .transactionId(transactionId)
                    .transactionDate(LocalDateTime.now())
                    .build();

            conversionRepository.save(conversion);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ConversionPersistenceException("Failed to create conversion due to exception: " + e.getMessage());
        }
    }
}
