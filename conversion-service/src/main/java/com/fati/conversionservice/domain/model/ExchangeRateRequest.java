package com.fati.conversionservice.domain.model;

import lombok.Builder;

@Builder
public record ExchangeRateRequest(String from, String to) {
}
