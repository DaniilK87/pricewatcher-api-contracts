package com.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductDto(
        UUID id,

        String name,

        String url,

        @JsonProperty("user_id")
        String userId,

        @JsonProperty("threshold_price")
        BigDecimal thresholdPrice
) {
}
