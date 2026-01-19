package br.dev.allissonnunes.algashop.product.catalog.application.product.query;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class ProductDetailOutputTestDataBuilder {

    private ProductDetailOutputTestDataBuilder() {

    }

    public static ProductDetailOutput.ProductDetailOutputBuilder aProduct() {
        return ProductDetailOutput.builder()
                .id(UUID.randomUUID())
                .addedAt(Instant.now())
                .name("Notebook X11")
                .brand("Deep Diver")
                .regularPrice(new BigDecimal("1500.00"))
                .salePrice(new BigDecimal("1000.00"))
                .inStock(true)
                .enabled(true)
                .category(CategoryMinimalOutput.builder()
                        .id(UUID.randomUUID())
                        .name("Notebook")
                        .build())
                .description("A Gamer Notebook");
    }

    public static ProductDetailOutput.ProductDetailOutputBuilder aProductAlt1() {
        return ProductDetailOutput.builder()
                .id(UUID.randomUUID())
                .addedAt(Instant.now())
                .name("Desktop I9000")
                .brand("Deep Diver")
                .regularPrice(new BigDecimal("3500.00"))
                .salePrice(new BigDecimal("3000.00"))
                .inStock(false)
                .enabled(true)
                .category(CategoryMinimalOutput.builder()
                        .id(UUID.randomUUID())
                        .name("Desktop")
                        .build())
                .description("A Gamer Desktop");
    }

}
