package com.fati.conversionlistservice;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author fati
 * @version 1.0
 * @since 18/Jun/2023
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String TRX_ID_HEADER_NAME = "X-Transaction-Id";
    public static final String TRX_ID_LOG_NAME = "transactionId";
}
