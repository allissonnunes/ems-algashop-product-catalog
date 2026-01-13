package br.dev.allissonnunes.algashop.product.catalog.presentation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductInput(
        @NotBlank
        String name,
        @NotBlank
        String brand,
        @NotNull
        BigDecimal regularPrice,
        @NotNull
        BigDecimal salePrice,
        @NotNull
        Boolean enabled,
        @NotNull
        UUID categoryId,
        String description
) {

}
