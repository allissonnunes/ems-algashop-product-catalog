package br.dev.allissonnunes.algashop.product.catalog.application.product.query;

import br.dev.allissonnunes.algashop.product.catalog.infrastructure.utility.Slugfier;
import lombok.Builder;

import java.io.Serializable;
import java.util.UUID;

@Builder
public record CategoryMinimalOutput(UUID id, String name, Boolean enabled, String slug) implements Serializable {

    public CategoryMinimalOutput {
        slug = Slugfier.slugify(name);
    }

}
