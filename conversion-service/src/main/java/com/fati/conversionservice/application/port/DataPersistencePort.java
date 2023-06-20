package com.fati.conversionservice.application.port;

import java.math.BigDecimal;

/**
 * @author fati
 * @version 1.0
 * @since 17/Jun/2023
 */

public interface DataPersistencePort {

    void create(String source, BigDecimal sourceAmount, String target, BigDecimal targetAmount, BigDecimal rate);
}
