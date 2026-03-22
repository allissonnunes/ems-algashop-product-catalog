package br.dev.allissonnunes.algashop.product.catalog.presentation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProductQuantityModel(
        @NotNull
        @Min(1)
        Integer quantity
) {

}
