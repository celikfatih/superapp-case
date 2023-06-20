package com.fati.exchangerateservice.domain.model;

import jakarta.validation.constraints.NotBlank;

public record ExchangeRateRequest(@NotBlank(message = "\"From\" field is must be not empty!") String from,
                                  @NotBlank(message = "\"To\" field is must be not empty!") String to) {
}
