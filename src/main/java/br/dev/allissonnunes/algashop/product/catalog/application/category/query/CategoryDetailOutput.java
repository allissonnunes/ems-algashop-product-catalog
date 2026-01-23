package br.dev.allissonnunes.algashop.product.catalog.application.category.query;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CategoryDetailOutput(
        UUID id,
        String name,
        Boolean enabled
) {

}
