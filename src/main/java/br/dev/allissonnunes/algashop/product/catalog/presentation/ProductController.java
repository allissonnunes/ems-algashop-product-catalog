package br.dev.allissonnunes.algashop.product.catalog.presentation;

import br.dev.allissonnunes.algashop.product.catalog.application.PageModel;
import br.dev.allissonnunes.algashop.product.catalog.application.product.management.ProductInput;
import br.dev.allissonnunes.algashop.product.catalog.application.product.management.ProductManagementApplicationService;
import br.dev.allissonnunes.algashop.product.catalog.application.product.query.ProductDetailOutput;
import br.dev.allissonnunes.algashop.product.catalog.application.product.query.ProductFilter;
import br.dev.allissonnunes.algashop.product.catalog.application.product.query.ProductQueryService;
import br.dev.allissonnunes.algashop.product.catalog.application.product.query.ProductSummaryOutput;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.category.CategoryNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.UUID;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
class ProductController {

    private final ProductManagementApplicationService productManagementApplicationService;

    private final ProductQueryService productQueryService;

    @PostMapping
    ResponseEntity<ProductDetailOutput> createProduct(@RequestBody final @Valid ProductInput input) {
        try {
            final ProductDetailOutput productDetailOutput = productManagementApplicationService.create(input);

            final var location = fromCurrentRequestUri().path("/{productId}")
                    .buildAndExpand(productDetailOutput.id())
                    .toUri();

            return ResponseEntity.created(location).body(productDetailOutput);
        } catch (final CategoryNotFoundException e) {
            throw new UnprocessableContentException(e.getMessage(), e);
        }
    }

    @GetMapping("/{productId}")
    ResponseEntity<ProductDetailOutput> findProductById(@PathVariable final UUID productId) {
        final ProductDetailOutput productDetailOutput = productQueryService.findById(productId);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(Duration.ofMinutes(1L)).cachePublic())
                .eTag("product:id:" + productDetailOutput.id() + ":v:" + productDetailOutput.version())
                .lastModified(productDetailOutput.lastModifiedAt().toInstant())
                .body(productDetailOutput);
    }

    @GetMapping
    ResponseEntity<PageModel<ProductSummaryOutput>> findProducts(final ProductFilter filter) {
        return ResponseEntity.ok(productQueryService.filter(filter));
    }

    @PutMapping("/{productId}")
    ResponseEntity<ProductDetailOutput> updateProductById(@PathVariable final UUID productId, @RequestBody final @Valid ProductInput input) {
        return ResponseEntity.ok(productManagementApplicationService.update(productId, input));
    }

    @DeleteMapping("/{productId}/enable")
    ResponseEntity<?> disable(@PathVariable final UUID productId) {
        productManagementApplicationService.disable(productId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{productId}/enable")
    ResponseEntity<?> enable(@PathVariable final UUID productId) {
        productManagementApplicationService.enable(productId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{productId}/restock")
    ResponseEntity<?> restock(@PathVariable final UUID productId, @RequestBody final @Valid ProductQuantityModel quantityModel) {
        productManagementApplicationService.restock(productId, quantityModel.quantity());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{productId}/withdraw")
    ResponseEntity<?> withdraw(@PathVariable final UUID productId, @RequestBody final @Valid ProductQuantityModel quantityModel) {
        productManagementApplicationService.withdraw(productId, quantityModel.quantity());
        return ResponseEntity.noContent().build();
    }

}
