package br.dev.allissonnunes.algashop.product.catalog.domain.model.product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProductPlacedOnSaleEvent(
        UUID productId,
        BigDecimal regularPrice,
        BigDecimal salePrice,
        Instant placedOnSaleAt
) {

}
