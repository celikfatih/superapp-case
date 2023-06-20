package com.fati.conversionservice.infrastructure.repository;

import com.fati.conversionservice.domain.model.Conversion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author fati
 * @version 1.0
 * @since 17/Jun/2023
 */
public interface ConversionRepository extends JpaRepository<Conversion, Long> {
}
