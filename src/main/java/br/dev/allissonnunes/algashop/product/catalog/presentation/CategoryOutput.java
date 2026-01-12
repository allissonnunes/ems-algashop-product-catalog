package br.dev.allissonnunes.algashop.product.catalog.presentation;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CategoryOutput(UUID id, String name) {

}
