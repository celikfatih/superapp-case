package com.fati.exchangerateservice.infrastructure.adapter;

import com.fati.exchangerateservice.application.usecase.GetExchangeRateUseCase;
import com.fati.exchangerateservice.domain.model.ExchangeRateRequest;
import com.fati.exchangerateservice.domain.model.ExchangeRateResponse;
import com.fati.exchangerateservice.infrastructure.exception.ExternalApiInvocationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fati
 * @version 1.0
 * @since 17/Jun/2023
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(path = {"/api/v1/rates"})
@Tag(name = ExchangeRateRestAdapter.EXCHANGE_RATE, description = "Exchange rate related operations.")
public class ExchangeRateRestAdapter {
    public static final String EXCHANGE_RATE = "Exchange Rate";

    private final GetExchangeRateUseCase getExchangeRateUseCase;

    @PostMapping(path = {"/get-exchange-rate"})
    @Operation(summary = "Gives the exchange rate between two currencies.", tags = { EXCHANGE_RATE })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The exchange rate request was successfully processed.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExchangeRateResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "At least one field is invalid! Please check your request.",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "External api invocation failed. Please try again later.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExternalApiInvocationException.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "An unexpected error occurred while processing the conversion request.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Exception.class))
            )
    })
    public ResponseEntity<ExchangeRateResponse> getExchangeRate(@Valid @RequestBody ExchangeRateRequest request) {
        ExchangeRateResponse exchangeRateResponse = getExchangeRateUseCase.get(request.from(), request.to());
        return ResponseEntity.ok(exchangeRateResponse);
    }
}
