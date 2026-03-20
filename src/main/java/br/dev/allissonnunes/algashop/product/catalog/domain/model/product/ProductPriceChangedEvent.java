package br.dev.allissonnunes.algashop.product.catalog.domain.model.product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProductPriceChangedEvent(
        UUID productId,
        BigDecimal oldRegularPrice,
        BigDecimal oldSalePrice,
        BigDecimal newRegularPrice,
        BigDecimal newSalePrice,
        Instant changedAt
) {

}
