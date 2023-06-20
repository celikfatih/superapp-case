package com.fati.conversionlistservice.application.port;

import com.fati.conversionlistservice.domain.model.Conversion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author fati
 * @version 1.0
 * @since 19/Jun/2023
 */
public interface DataPersistencePort {

    @Transactional(readOnly = true)
    Page<Conversion> getByTransactionId(String transactionId, Pageable pageable);

    @Transactional(readOnly = true)
    Page<Conversion> getByTransactionDateAfter(LocalDateTime transactionDate, Pageable pageable);
}
