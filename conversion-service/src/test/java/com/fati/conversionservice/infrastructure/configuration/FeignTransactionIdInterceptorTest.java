package com.fati.conversionservice.infrastructure.configuration;

import feign.RequestTemplate;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FeignTransactionIdInterceptorTest {

    private static final String TRX_ID_HEADER_NAME = "X-Transaction-Id";
    private static final String TRX_ID_LOG_NAME = "transactionId";

    @Test
    void addTransactionIdHeader() {
        String transactionId = "123456";

        MDC.put(TRX_ID_LOG_NAME, transactionId);
        RequestTemplate template = new RequestTemplate();
        FeignTransactionIdInterceptor interceptor = new FeignTransactionIdInterceptor();
        interceptor.apply(template);

        assertEquals(Collections.singletonList(transactionId), template.headers().get(TRX_ID_HEADER_NAME));
    }
}