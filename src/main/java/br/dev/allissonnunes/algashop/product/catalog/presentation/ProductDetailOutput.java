package br.dev.allissonnunes.algashop.product.catalog.presentation;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
public record ProductDetailOutput(
        UUID id,
        Instant addedAt,
        String name,
        String brand,
        BigDecimal regularPrice,
        BigDecimal salePrice,
        Boolean inStock,
        Boolean enabled,
        CategoryMinimalOutput category,
        String description
) {

}
