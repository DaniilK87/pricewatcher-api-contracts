package com.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PriceDtoTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void shouldSerializeAllFields() throws Exception {
        PriceDto priceDto = new PriceDto(
                UUID.fromString("b0f00387-01e1-455f-8d2d-88b6c4d91168"),
                UUID.fromString("0b9d0001-5494-440c-8488-b4ff62335e4c"),
                new BigDecimal(200.0),
                LocalDateTime.of(2024, 1, 15, 14, 30, 0));

        String json = objectMapper.writeValueAsString(priceDto);

        assertThat(json)
                .contains("b0f00387-01e1-455f-8d2d-88b6c4d91168")
                .contains("0b9d0001-5494-440c-8488-b4ff62335e4c")
                .contains("200")
                .contains("2024-01-15T14:30:00");
    }

    @Test
    void shouldDeserializeFromJson() throws Exception {
        String json = """
            {
                "id": "b0f00387-01e1-455f-8d2d-88b6c4d91168",
                "product_id": "0b9d0001-5494-440c-8488-b4ff62335e4c",
                "price": "200",
                "timestamp": "2024-01-15T14:30:00"
            }
            """;

        PriceDto dto = objectMapper.readValue(json, PriceDto.class);

        assertThat(dto.id()).hasToString( "b0f00387-01e1-455f-8d2d-88b6c4d91168");
        assertThat(dto.productId()).hasToString("0b9d0001-5494-440c-8488-b4ff62335e4c");
        assertThat(dto.price()).isEqualTo(new BigDecimal(200));
        assertThat(dto.timestamp()).isEqualTo(LocalDateTime.of(2024, 1, 15, 14, 30, 0));
    }

    @Test
    void shouldOmitNullFields() throws Exception {
        PriceDto priceDto = new PriceDto(
                UUID.fromString("b0f00387-01e1-455f-8d2d-88b6c4d91168"),
                UUID.fromString("0b9d0001-5494-440c-8488-b4ff62335e4c"),
                null,
                LocalDateTime.of(2024, 1, 15, 14, 30, 0));

        String json = objectMapper.writeValueAsString(priceDto);

        assertThat(json).doesNotContain("price");
    }
}
