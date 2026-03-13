package br.dev.allissonnunes.algashop.product.catalog.application.product.query;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
public record ProductSummaryOutput(
        UUID id,
        Instant addedAt,
        String name,
        String brand,
        String slug,
        BigDecimal regularPrice,
        BigDecimal salePrice,
        Boolean hasDiscount,
        Boolean inStock,
        Boolean enabled,
        Integer quantityInStock,
        Integer discountPercentageRounded,
        CategoryMinimalOutput category,
        String shortDescription
) {

}
