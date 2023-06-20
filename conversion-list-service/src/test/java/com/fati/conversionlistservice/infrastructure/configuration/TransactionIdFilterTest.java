package com.fati.conversionlistservice.infrastructure.configuration;

import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionIdFilterTest {

    private static final String TRX_ID_HEADER_NAME = "X-Transaction-Id";

    @Mock
    private MockHttpServletRequest request;

    @Mock
    private MockHttpServletResponse response;

    @Mock
    private MockFilterChain filterChain;

    @Captor
    private ArgumentCaptor<String> transactionIdCaptor;

    private final TransactionIdFilter transactionIdFilter = new TransactionIdFilter();

    @Test
    void testDoFilterWhenRequestIncludeTransactionIdShouldExistTransactionIdPutMdc() throws ServletException,
            IOException {
        String trx = "123456";
        when(request.getHeader(TRX_ID_HEADER_NAME)).thenReturn(trx);

        transactionIdFilter.doFilter(request, response, filterChain);

        assertEquals(trx, request.getHeader(TRX_ID_HEADER_NAME));
    }

    @Test
    void testDoFilterWhenRequestNotIncludeTransactionIdShouldGeneratedNewTransactionIdPutMdc() throws ServletException,
            IOException {
        when(request.getHeader(TRX_ID_HEADER_NAME)).thenReturn(null);

        transactionIdFilter.doFilter(request, response, filterChain);

        assertNull(request.getHeader(TRX_ID_HEADER_NAME));
    }
}