package com.fati.conversionlistservice.application.usecase;

import com.fati.conversionlistservice.domain.model.ConversionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

/**
 * @author fati
 * @version 1.0
 * @since 19/Jun/2023
 */
public interface GetConversionListUseCase {

    Page<ConversionDto> get(String transactionId, LocalDateTime transactionDate, Pageable pageable);
}
