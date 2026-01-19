package br.dev.allissonnunes.algashop.product.catalog.presentation;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CategoryMinimalOutput(UUID id, String name) {

}
