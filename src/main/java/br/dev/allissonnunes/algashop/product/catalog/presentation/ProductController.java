package br.dev.allissonnunes.algashop.product.catalog.presentation;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri;

@RestController
@RequestMapping("/api/v1/products")
class ProductController {

    @PostMapping
    ResponseEntity<ProductDetailOutput> createProduct(@RequestBody final @Valid ProductInput input) {
        ProductDetailOutput productDetailOutput = ProductDetailOutput.builder()
                .id(UUID.randomUUID())
                .addedAt(Instant.now())
                .name(input.name())
                .brand(input.brand())
                .regularPrice(input.regularPrice())
                .salePrice(input.salePrice())
                .inStock(false)
                .enabled(input.enabled())
                .category(CategoryMinimalOutput.builder()
                        .id(input.categoryId())
                        .name("Notebook")
                        .build())
                .description(input.description())
                .build();

        final var location = fromCurrentRequestUri().path("/{productId}")
                .buildAndExpand(productDetailOutput.id())
                .toUri();

        return ResponseEntity.created(location).body(productDetailOutput);
    }

    @GetMapping("/{productId}")
    ResponseEntity<ProductDetailOutput> findProductById(@PathVariable final UUID productId) {
        ProductDetailOutput productDetailOutput = ProductDetailOutput.builder()
                .id(productId)
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
                .description("A Gamer Notebook")
                .build();
        return ResponseEntity.ok(productDetailOutput);
    }

    @GetMapping
    ResponseEntity<PageModel<ProductDetailOutput>> findProducts(final Integer page, final Integer size) {
        return ResponseEntity.ok(PageModel.<ProductDetailOutput>builder()
                .content(List.of(
                        ProductDetailOutput.builder()
                                .id(UUID.randomUUID())
                                .addedAt(Instant.now())
                                .name("Notebook X11")
                                .brand("Deep Diver")
                                .regularPrice(new BigDecimal("1500.00"))
                                .salePrice(new BigDecimal("1000.00"))
                                .inStock(true)
                                .enabled(true)
                                .category(CategoryMinimalOutput.builder().id(UUID.randomUUID()).name("Notebook").build())
                                .description("A Gamer Notebook")
                                .build(),
                        ProductDetailOutput.builder()
                                .id(UUID.randomUUID())
                                .addedAt(Instant.now())
                                .name("Desktop I9000")
                                .brand("Deep Diver")
                                .regularPrice(new BigDecimal("3500.00"))
                                .salePrice(new BigDecimal("3000.00"))
                                .inStock(false)
                                .enabled(true)
                                .category(CategoryMinimalOutput.builder().id(UUID.randomUUID()).name("Desktop").build())
                                .description("A Gamer Desktop")
                                .build()
                ))
                .number(0)
                .size(size == null ? 10 : size)
                .totalPages(1)
                .totalElements(2L)
                .build());
    }

}
