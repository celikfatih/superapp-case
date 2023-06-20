package com.fati.conversionservice.infrastructure.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static com.fati.conversionservice.Constants.TRX_ID_HEADER_NAME;
import static com.fati.conversionservice.Constants.TRX_ID_LOG_NAME;

/**
 * @author fati
 * @version 1.0
 * @since 18/Jun/2023
 */

@Slf4j
@Component
public class FeignTransactionIdInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        String transactionId = MDC.get(TRX_ID_LOG_NAME);
        if (StringUtils.hasText(transactionId)) {
            if (log.isDebugEnabled()) {
                log.debug("transactionId: {} is being added to the feign request.", transactionId);
            }
            template.header(TRX_ID_HEADER_NAME, transactionId);
        }
    }
}
