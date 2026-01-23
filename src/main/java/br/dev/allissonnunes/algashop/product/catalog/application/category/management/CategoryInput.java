package br.dev.allissonnunes.algashop.product.catalog.application.category.management;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryInput(
        @NotBlank
        String name,
        @NotNull
        Boolean enabled
) {

}
