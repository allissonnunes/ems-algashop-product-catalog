package br.dev.allissonnunes.algashop.product.catalog.application.upload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UploadRequestInput(
        @NotBlank
        String originalFileName,
        @NotNull
        Long contentLength
) {

}
