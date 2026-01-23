package br.dev.allissonnunes.algashop.product.catalog.application.category.query;

import java.util.UUID;

public class CategoryDetailOutputTestDataBuilder {

    private CategoryDetailOutputTestDataBuilder() {

    }

    public static CategoryDetailOutput.CategoryDetailOutputBuilder aCategoryDetailOutput() {
        return CategoryDetailOutput.builder()
                .id(UUID.randomUUID())
                .name("Notebooks")
                .enabled(true);
    }

    public static CategoryDetailOutput.CategoryDetailOutputBuilder aCategoryDetailOutputAlt1() {
        return CategoryDetailOutput.builder()
                .id(UUID.randomUUID())
                .name("Desktops")
                .enabled(true);
    }

}
