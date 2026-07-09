package br.dev.allissonnunes.algashop.product.catalog.presentation;

import br.dev.allissonnunes.algashop.product.catalog.application.product.management.ImageInput;
import br.dev.allissonnunes.algashop.product.catalog.application.product.management.ProductImageManagementApplicationService;
import br.dev.allissonnunes.algashop.product.catalog.application.product.query.ImageOutput;
import br.dev.allissonnunes.algashop.product.catalog.application.product.query.ProductImagesQueryService;
import br.dev.allissonnunes.algashop.product.catalog.infrastructure.security.SecurityAnnotations;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products/{productId}/images")
@RequiredArgsConstructor
class ProductImagesController {

    private final ProductImageManagementApplicationService managementService;

    private final ProductImagesQueryService queryService;

    @SecurityAnnotations.CanWriteProducts
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ImageOutput create(@PathVariable UUID productId,
                       @RequestBody @Valid ImageInput input) {
        return managementService.create(productId, input);
    }

    @SecurityAnnotations.CanWriteProducts
    @DeleteMapping("{imageId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable UUID productId, @PathVariable UUID imageId) {
        managementService.delete(productId, imageId);
    }

    @SecurityAnnotations.CanWriteProducts
    @PutMapping("{imageId}/primary")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void primary(@PathVariable UUID productId, @PathVariable UUID imageId) {
        managementService.primary(productId, imageId);
    }

    @SecurityAnnotations.CanReadProducts
    @GetMapping
    List<ImageOutput> getAll(@PathVariable UUID productId) {
        return queryService.getAllImages(productId);
    }

    @SecurityAnnotations.CanReadProducts
    @GetMapping("{imageId}")
    ImageOutput getOne(@PathVariable UUID productId, @PathVariable UUID imageId) {
        return queryService.getImage(productId, imageId);
    }

}
