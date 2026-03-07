package com.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProductDtoTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void shouldSerializeAllFields() throws Exception {
        ProductDto productDto = new ProductDto(
                UUID.fromString("b0f00387-01e1-455f-8d2d-88b6c4d91168"),
                "test",
                  "http://test.ru",
                "225566",
                new BigDecimal(250.0));

        String json = objectMapper.writeValueAsString(productDto);

        assertThat(json)
                .contains("b0f00387-01e1-455f-8d2d-88b6c4d91168")
                .contains("test")
                .contains("225566")
                .contains("250");
    }

    @Test
    void shouldDeserializeFromJson() throws Exception {
        String json = """
            {
                "id": "b0f00387-01e1-455f-8d2d-88b6c4d91168",
                "name": "test",
                "url": "http://test.ru",
                "user_id": "225566",
                "threshold_price": 250
            }
            """;

        ProductDto dto = objectMapper.readValue(json, ProductDto.class);

        assertThat(dto.id()).hasToString("b0f00387-01e1-455f-8d2d-88b6c4d91168");
        assertThat(dto.name()).isEqualTo("test");
        assertThat(dto.userId()).isEqualTo("225566");
        assertThat(dto.thresholdPrice()).isEqualTo(new BigDecimal(250));
    }

    @Test
    void shouldOmitNullFields() throws Exception {
        ProductDto dto = new ProductDto(
                UUID.fromString("b0f00387-01e1-455f-8d2d-88b6c4d91168"),
                "test",
                "http://test.ru",
                "225566",
                null
        );

        String json = objectMapper.writeValueAsString(dto);

        assertThat(json).doesNotContain("threshold_price");
    }
}
