package com.fati.conversionlistservice.infrastructure.repository;

import com.fati.conversionlistservice.domain.model.Conversion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

/**
 * @author fati
 * @version 1.0
 * @since 17/Jun/2023
 */
public interface ConversionRepository extends JpaRepository<Conversion, Long> {

    Page<Conversion> getAllByTransactionId(String transactionId, Pageable pageable);

    Page<Conversion> getAllByTransactionDateAfter(LocalDateTime transactionDate, Pageable pageable);
}
