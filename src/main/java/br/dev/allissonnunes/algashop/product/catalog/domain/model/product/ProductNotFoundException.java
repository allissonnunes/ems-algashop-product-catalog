package br.dev.allissonnunes.algashop.product.catalog.domain.model.product;

import br.dev.allissonnunes.algashop.product.catalog.domain.model.DomainEntityNotFoundException;

import java.util.UUID;

public class ProductNotFoundException extends DomainEntityNotFoundException {

    public ProductNotFoundException(final UUID productId) {
        super("Product with ID %s not found".formatted(productId));
    }

}
