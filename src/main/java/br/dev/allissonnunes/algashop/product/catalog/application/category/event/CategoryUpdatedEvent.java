package br.dev.allissonnunes.algashop.product.catalog.application.category.event;

import java.util.UUID;

public record CategoryUpdatedEvent(UUID id, String name, Boolean enabled) {

}
