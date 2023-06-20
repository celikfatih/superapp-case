package com.fati.conversionservice.infrastructure.adapter;

import com.fati.conversionservice.application.usecase.ConvertAmountUseCase;
import com.fati.conversionservice.domain.model.ConversionRequest;
import com.fati.conversionservice.domain.model.ConversionResponse;
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
@RequestMapping(path = {"/api/v1/conversions"})
@Tag(name = ConversionRestAdapter.CONVERSION, description = "Currency conversion related operations.")
public class ConversionRestAdapter {
    public static final String CONVERSION = "Conversion";

    private final ConvertAmountUseCase convertAmountUseCase;

    @PostMapping(path = {"/convert"})
    @Operation(summary = "Allows conversion from source currency to target currency. Gives the amount of target " +
            "currency corresponding to the amount of source currency.", tags = { CONVERSION })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Conversion request successfully processed.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConversionResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "At least one field is invalid! Please check your request.",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "An unexpected error occurred while processing the conversion request.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Exception.class))
            )
    })
    public ResponseEntity<ConversionResponse> convert(@Valid @RequestBody ConversionRequest conversionRequest) {
        ConversionResponse conversionResponse = convertAmountUseCase.convert(conversionRequest.source(),
                conversionRequest.sourceAmount(),
                conversionRequest.target());
        return ResponseEntity.ok(conversionResponse);
    }
}
