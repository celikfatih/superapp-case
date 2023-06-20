package com.fati.conversionlistservice.infrastructure.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fati.conversionlistservice.application.usecase.GetConversionListUseCase;
import com.fati.conversionlistservice.domain.model.ConversionDto;
import com.fati.conversionlistservice.domain.model.ConversionListRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {ConversionListRestAdapter.class})
class ConversionListRestAdapterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GetConversionListUseCase getConversionListUseCase;

    @Test
    void testGetWhenValidInputsShouldReturn200() throws Exception {
        String transactionId = "123456";
        ConversionDto conversionDto = new ConversionDto("source", BigDecimal.TEN, "target", BigDecimal.ONE,
                BigDecimal.ZERO, transactionId, LocalDateTime.now());
        ConversionListRequest request = new ConversionListRequest(transactionId, null);

        when(getConversionListUseCase.get(eq(transactionId), eq(null), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(conversionDto)));

        mockMvc.perform(post("/api/v1/conversion-list/get?page=0")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].targetAmount").value(BigDecimal.ONE));
    }
}