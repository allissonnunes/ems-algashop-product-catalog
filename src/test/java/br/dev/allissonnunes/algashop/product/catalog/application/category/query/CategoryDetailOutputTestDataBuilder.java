package br.dev.allissonnunes.algashop.product.catalog.application.category.query;

import java.time.Instant;
import java.util.UUID;

public class CategoryDetailOutputTestDataBuilder {

    private CategoryDetailOutputTestDataBuilder() {

    }

    public static CategoryDetailOutput.CategoryDetailOutputBuilder aCategoryDetailOutput() {
        return CategoryDetailOutput.builder()
                .id(UUID.randomUUID())
                .name("Notebooks")
                .enabled(true)
                .version(0L)
                .lastModifiedAt(Instant.now());
    }

    public static CategoryDetailOutput.CategoryDetailOutputBuilder aCategoryDetailOutputAlt1() {
        return CategoryDetailOutput.builder()
                .id(UUID.randomUUID())
                .name("Desktops")
                .enabled(true)
                .version(0L)
                .lastModifiedAt(Instant.now());
    }

}
