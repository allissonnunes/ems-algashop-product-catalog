package br.dev.allissonnunes.algashop.product.catalog.domain.model.category;

import br.dev.allissonnunes.algashop.product.catalog.domain.model.DomainEntityNotFoundException;

import java.util.UUID;

public class CategoryNotFoundException extends DomainEntityNotFoundException {

    public CategoryNotFoundException(final UUID categoryId) {
        super("Category with ID %s not found".formatted(categoryId));
    }

}
