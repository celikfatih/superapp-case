package com.fati.conversionlistservice.infrastructure.configuration;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.UUID;
import java.util.function.Supplier;

import static com.fati.conversionlistservice.Constants.TRX_ID_HEADER_NAME;
import static com.fati.conversionlistservice.Constants.TRX_ID_LOG_NAME;

/**
 * @author fati
 * @version 1.0
 * @since 18/Jun/2023
 */

@Slf4j
@Component
public class TransactionIdFilter implements Filter {

    private static final Supplier<String> GET_RANDOM_TRX = () -> UUID.randomUUID().toString()
            .replace("-", "");

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        try {
            final HttpServletRequest request = (HttpServletRequest) req;
            String transactionId = request.getHeader(TRX_ID_HEADER_NAME);

            if (StringUtils.hasText(transactionId)) {
                if (log.isDebugEnabled()) {
                    log.debug("Request header contain a transactionId: {}", transactionId);
                }
                MDC.put(TRX_ID_LOG_NAME, transactionId);
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Request header does not contain a transactionId. Adding a new one.");
                }
                MDC.put(TRX_ID_LOG_NAME, GET_RANDOM_TRX.get());
            }

            chain.doFilter(req, res);
        } finally {
            MDC.remove(TRX_ID_HEADER_NAME);
        }
    }
}
