package com.fati.conversionservice.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author fati
 * @version 1.0
 * @since 17/Jun/2023
 */

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Conversion implements Serializable {

    @Serial
    private static final long serialVersionUID = 6803031460330981449L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String source;
    private BigDecimal sourceAmount;
    private String target;
    private BigDecimal targetAmount;
    private BigDecimal rate;
    private String transactionId;
    private LocalDateTime transactionDate;
}
