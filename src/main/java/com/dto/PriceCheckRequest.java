package com.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record PriceCheckRequest(

        @JsonProperty("product_id")
        UUID productId,

        String url
) {
}
