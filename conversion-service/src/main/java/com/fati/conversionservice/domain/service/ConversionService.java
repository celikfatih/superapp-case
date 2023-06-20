package com.fati.conversionservice.domain.service;

import com.fati.conversionservice.application.port.DataPersistencePort;
import com.fati.conversionservice.application.port.InternalApiInvocationPort;
import com.fati.conversionservice.application.usecase.ConvertAmountUseCase;
import com.fati.conversionservice.domain.model.ConversionResponse;
import com.fati.conversionservice.domain.model.ExchangeRateResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.fati.conversionservice.Constants.TRX_ID_LOG_NAME;

/**
 * @author fati
 * @version 1.0
 * @since 17/Jun/2023
 */

@Service
@RequiredArgsConstructor
public class ConversionService implements ConvertAmountUseCase {

    private final InternalApiInvocationPort internalApiInvocationPort;
    private final DataPersistencePort dataPersistencePort;

    @Override
    public ConversionResponse convert(String source, BigDecimal sourceAmount, String target) {
        ExchangeRateResponse response = internalApiInvocationPort.invoke(source, target);
        final BigDecimal targetAmount = response.rate().multiply(sourceAmount).setScale(2, RoundingMode.CEILING);
        dataPersistencePort.create(source, sourceAmount, target, targetAmount, response.rate());
        return ConversionResponse.builder()
                .targetAmount(targetAmount)
                .transactionId(MDC.get(TRX_ID_LOG_NAME))
                .build();
    }
}
