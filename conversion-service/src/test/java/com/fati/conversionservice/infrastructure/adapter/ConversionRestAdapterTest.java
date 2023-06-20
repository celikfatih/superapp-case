package com.fati.conversionservice.infrastructure.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fati.conversionservice.application.usecase.ConvertAmountUseCase;
import com.fati.conversionservice.domain.model.ConversionRequest;
import com.fati.conversionservice.domain.model.ConversionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {ConversionRestAdapter.class})
class ConversionRestAdapterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ConvertAmountUseCase convertAmountUseCase;


    @Test
    void testConvertWhenValidInputsShouldReturn200() throws Exception {
        String source = "USD";
        String target = "TRY";
        BigDecimal sourceAmount = BigDecimal.ONE;
        ConversionRequest request = new ConversionRequest(source, target, sourceAmount);

        when(convertAmountUseCase.convert(source, sourceAmount, target))
                .thenReturn(new ConversionResponse(BigDecimal.TEN, ""));

        mockMvc.perform(post("/api/v1/conversions/convert")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.targetAmount").value(BigDecimal.TEN));
    }

    @Test
    void testConvertWhenNotValidInputShouldReturn400() throws Exception {
        String source = "USD";
        String target = "TRY";
        BigDecimal sourceAmount = BigDecimal.ZERO;
        ConversionRequest request = new ConversionRequest(source, target, sourceAmount);

        mockMvc.perform(post("/api/v1/conversions/convert")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}