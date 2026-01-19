package br.dev.allissonnunes.algashop.product.catalog.application.product.query;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CategoryMinimalOutput(UUID id, String name) {

}
