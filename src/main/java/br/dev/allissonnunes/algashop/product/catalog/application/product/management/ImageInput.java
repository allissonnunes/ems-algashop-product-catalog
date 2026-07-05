package br.dev.allissonnunes.algashop.product.catalog.application.product.management;

import jakarta.validation.constraints.NotBlank;

public record ImageInput(
        @NotBlank
        String remoteFileName
) {

}
