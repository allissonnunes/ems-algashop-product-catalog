package br.dev.allissonnunes.algashop.product.catalog.domain.model.product;

import br.dev.allissonnunes.algashop.product.catalog.domain.model.category.Category;

import java.util.UUID;

public record ProductCategory(
        UUID id,
        String name,
        Boolean enabled
) {

    public static ProductCategory of(final Category category) {
        return new ProductCategory(category.getId(), category.getName(), category.getEnabled());
    }

}
