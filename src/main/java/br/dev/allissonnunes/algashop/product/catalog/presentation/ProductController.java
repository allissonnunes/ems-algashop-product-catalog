package br.dev.allissonnunes.algashop.product.catalog.presentation;

import br.dev.allissonnunes.algashop.product.catalog.application.product.management.ProductInput;
import br.dev.allissonnunes.algashop.product.catalog.application.product.management.ProductManagementApplicationService;
import br.dev.allissonnunes.algashop.product.catalog.application.product.query.PageModel;
import br.dev.allissonnunes.algashop.product.catalog.application.product.query.ProductDetailOutput;
import br.dev.allissonnunes.algashop.product.catalog.application.product.query.ProductQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
class ProductController {

    private final ProductManagementApplicationService productManagementApplicationService;

    private final ProductQueryService productQueryService;

    @PostMapping
    ResponseEntity<ProductDetailOutput> createProduct(@RequestBody final @Valid ProductInput input) {
        final UUID productId = productManagementApplicationService.create(input);
        final ProductDetailOutput productDetailOutput = productQueryService.findById(productId);

        final var location = fromCurrentRequestUri().path("/{productId}")
                .buildAndExpand(productDetailOutput.id())
                .toUri();

        return ResponseEntity.created(location).body(productDetailOutput);
    }

    @GetMapping("/{productId}")
    ResponseEntity<ProductDetailOutput> findProductById(@PathVariable final UUID productId) {
        final ProductDetailOutput productDetail = productQueryService.findById(productId);
        return ResponseEntity.ok(productDetail);
    }

    @GetMapping
    ResponseEntity<PageModel<ProductDetailOutput>> findProducts(
            @RequestParam(name = "number", required = false) final Integer page,
            @RequestParam(name = "size", required = false) final Integer size) {
        return ResponseEntity.ok(productQueryService.filter(page, size));
    }

    @PutMapping("/{productId}")
    ResponseEntity<ProductDetailOutput> updateProductById(@PathVariable final UUID productId, @RequestBody final @Valid ProductInput input) {
        productManagementApplicationService.update(productId, input);
        final ProductDetailOutput updatedProductDetail = productQueryService.findById(productId);
        return ResponseEntity.ok(updatedProductDetail);
    }

    @DeleteMapping("/{productId}")
    ResponseEntity<?> deleteProductById(@PathVariable UUID productId) {
        productManagementApplicationService.disable(productId);
        return ResponseEntity.noContent().build();
    }

}
