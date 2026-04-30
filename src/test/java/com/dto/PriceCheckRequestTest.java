package com.dto;

import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PriceCheckRequestTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldSerializeAllFields() {
        PriceCheckRequest priceCheckRequest = new PriceCheckRequest(
                UUID.fromString("0b9d0001-5494-440c-8488-b4ff62335e4c"),
                "http://test.ru");

        String json = objectMapper.writeValueAsString(priceCheckRequest);

        assertThat(json)
                .contains("0b9d0001-5494-440c-8488-b4ff62335e4c")
                .contains("http:\\/\\/test.ru");
    }

    @Test
    void shouldDeserializeFromJson() {
        String json = """
            {
                "product_id": "0b9d0001-5494-440c-8488-b4ff62335e4c",
                "url": "http://test.ru"
            }
            """;

        PriceCheckRequest dto = objectMapper.readValue(json, PriceCheckRequest.class);

        assertThat(dto.productId()).hasToString("0b9d0001-5494-440c-8488-b4ff62335e4c");
        assertThat(dto.url()).isEqualTo("http://test.ru");
    }
}
