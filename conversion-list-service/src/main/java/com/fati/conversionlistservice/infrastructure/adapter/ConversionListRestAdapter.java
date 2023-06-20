package com.fati.conversionlistservice.infrastructure.adapter;

import com.fati.conversionlistservice.application.usecase.GetConversionListUseCase;
import com.fati.conversionlistservice.domain.model.ConversionDto;
import com.fati.conversionlistservice.domain.model.ConversionListRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fati
 * @version 1.0
 * @since 19/Jun/2023
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(path = {"/api/v1/conversion-list"})
@Tag(name = ConversionListRestAdapter.CONVERSION_LIST, description = "Currency conversion list related operations.")
public class ConversionListRestAdapter {

    public static final String CONVERSION_LIST = "Conversion List";

    private final GetConversionListUseCase getConversionListUseCase;

    @PageableAsQueryParam
    @PostMapping(path = {"/get"})
    @Operation(summary = "Returns a list of all currency conversions (transactionId and transactionDate " +
            "fields can be used for filtering).", tags = { CONVERSION_LIST })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Conversion list request successfully processed.",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = ConversionDto.class)))),
            @ApiResponse(
                    responseCode = "400",
                    description = "At least one field is invalid! Please check your request.",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "An unexpected error occurred while processing the conversion list request.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Exception.class))
            )
    })
    public ResponseEntity<Page<ConversionDto>> get(@RequestBody ConversionListRequest request,
                                                   @Parameter(hidden = true)
                                                   @PageableDefault(size = 20)
                                                   @SortDefault.SortDefaults(
                                                        @SortDefault(sort = "transactionDate",
                                                                direction = Sort.Direction.DESC)) Pageable pageable) {
        Page<ConversionDto> conversions = getConversionListUseCase.get(request.transactionId(),
                request.transactionDate(), pageable);
        return ResponseEntity.ok(conversions);
    }
}
