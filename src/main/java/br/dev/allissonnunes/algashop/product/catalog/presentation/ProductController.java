package br.dev.allissonnunes.algashop.product.catalog.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @PostMapping
    public ResponseEntity<ProductDetailOutput> createProduct(@RequestBody final ProductInput input) {
        ProductDetailOutput productDetailOutput = ProductDetailOutput.builder()
                .id(UUID.randomUUID())
                .addedAt(Instant.now())
                .name(input.name())
                .brand(input.brand())
                .regularPrice(input.regularPrice())
                .salePrice(input.salePrice())
                .inStock(false)
                .enabled(input.enabled())
                .category(CategoryOutput.builder().id(input.categoryId()).name("Notebook").build())
                .description(input.description())
                .build();

        final var location = fromCurrentRequestUri().path("/{productId}")
                .buildAndExpand(productDetailOutput.id())
                .toUri();

        return ResponseEntity.created(location).body(productDetailOutput);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailOutput> findProductById(@PathVariable final UUID productId) {
        ProductDetailOutput productDetailOutput = ProductDetailOutput.builder()
                .id(productId)
                .addedAt(Instant.now())
                .name("Notebook X11")
                .brand("Deep Diver")
                .regularPrice(new BigDecimal("1500.00"))
                .salePrice(new BigDecimal("1000.00"))
                .inStock(true)
                .enabled(true)
                .category(CategoryOutput.builder().id(UUID.randomUUID()).name("Notebook").build())
                .description("A Gamer Notebook")
                .build();
        return ResponseEntity.ok(productDetailOutput);
    }

}
