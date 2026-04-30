package com.dto;

import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PriceUpdateEventTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldSerializeAllFields() {
        PriceUpdateEvent priceUpdateEvent = new PriceUpdateEvent(
                UUID.fromString("0b9d0001-5494-440c-8488-b4ff62335e4c"),
                new BigDecimal(100.0),
                new BigDecimal(150.0),
                LocalDateTime.of(2024, 1, 15, 14, 30, 0));

        String json = objectMapper.writeValueAsString(priceUpdateEvent);

        assertThat(json)
                .contains("0b9d0001-5494-440c-8488-b4ff62335e4c")
                .contains("100")
                .contains("150")
                .contains("2024-01-15T14:30:00");
    }

    @Test
    void shouldDeserializeFromJson() {
        String json = """
            {
                "product_id": "0b9d0001-5494-440c-8488-b4ff62335e4c",
                "old_price": "200",
                "new_price": "250",
                "timestamp": "2024-01-15T14:30:00"
            }
            """;

        PriceUpdateEvent dto = objectMapper.readValue(json, PriceUpdateEvent.class);

        assertThat(dto.productId()).hasToString( "0b9d0001-5494-440c-8488-b4ff62335e4c");
        assertThat(dto.oldPrice()).isEqualTo(new BigDecimal(200));
        assertThat(dto.newPrice()).isEqualTo(new BigDecimal(250));
        assertThat(dto.timestamp()).isEqualTo(LocalDateTime.of(2024, 1, 15, 14, 30, 0));
    }

    @Test
    void shouldOmitNullFields() {
        PriceUpdateEvent priceUpdateEvent = new PriceUpdateEvent(
                UUID.fromString("b0f00387-01e1-455f-8d2d-88b6c4d91168"),
                null,
                null,
                LocalDateTime.of(2024, 1, 15, 14, 30, 0));

        String json = objectMapper.writeValueAsString(priceUpdateEvent);

        assertThat(json).doesNotContain("old_price");
        assertThat(json).doesNotContain("new_price");
    }
}
