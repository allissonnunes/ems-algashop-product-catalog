package br.dev.allissonnunes.algashop.product.catalog.presentation;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductInput(
        String name,
        String brand,
        BigDecimal regularPrice,
        BigDecimal salePrice,
        Boolean enabled,
        UUID categoryId,
        String description
) {

}
