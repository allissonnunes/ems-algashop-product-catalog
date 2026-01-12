package br.dev.allissonnunes.algashop.product.catalog.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailOutput> findProductById(@PathVariable final UUID productId) {
        ProductDetailOutput productDetailOutput = ProductDetailOutput.builder()
                .id(productId)
                .addedAt(Instant.now())
                .name("Notebook X11")
                .brand("Deep Diver")
                .regularPrice(new BigDecimal("1500.00"))
                .salePrice(new BigDecimal("1000.00"))
                .inStock(false)
                .enabled(true)
                .categoryId(UUID.randomUUID())
                .description("A Gamer Notebook")
                .build();
        return ResponseEntity.ok(productDetailOutput);
    }

}
