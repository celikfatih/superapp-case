package com.fati.exchangerateservice.infrastructure.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fati.exchangerateservice.application.usecase.GetExchangeRateUseCase;
import com.fati.exchangerateservice.domain.model.ExchangeRateRequest;
import com.fati.exchangerateservice.domain.model.ExchangeRateResponse;
import com.fati.exchangerateservice.infrastructure.exception.ExternalApiInvocationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {ExchangeRateRestAdapter.class})
class ExchangeRateRestAdapterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GetExchangeRateUseCase getExchangeRateUseCase;

    @Test
    void testGetExchangeRateWhenValidInputShouldReturn200() throws Exception {
        String from = "EUR";
        String to = "USD";
        ExchangeRateRequest request = new ExchangeRateRequest(from, to);
        ExchangeRateResponse response = new ExchangeRateResponse(BigDecimal.TEN);

        when(getExchangeRateUseCase.get(from, to)).thenReturn(response);

        mockMvc.perform(post("/api/v1/rates/get-exchange-rate")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rate").value(BigDecimal.TEN));
    }

    @Test
    void testGetExchangeRateWhenNotValidInputShouldReturn400() throws Exception {
        String from = "EUR";
        String to = "";
        ExchangeRateRequest request = new ExchangeRateRequest(from, to);

        mockMvc.perform(post("/api/v1/rates/get-exchange-rate")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetExchangeRateWhenExternalCallFailedShouldReturn409() throws Exception {
        String from = "EUR";
        String to = "USD";
        ExchangeRateRequest request = new ExchangeRateRequest(from, to);
        ExchangeRateResponse response = new ExchangeRateResponse(BigDecimal.TEN);

        when(getExchangeRateUseCase.get(from, to)).thenThrow(ExternalApiInvocationException.class);

        mockMvc.perform(post("/api/v1/rates/get-exchange-rate")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(res -> assertTrue(res.getResolvedException() instanceof ExternalApiInvocationException));
    }
}