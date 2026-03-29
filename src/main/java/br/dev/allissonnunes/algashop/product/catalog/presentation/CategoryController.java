package br.dev.allissonnunes.algashop.product.catalog.presentation;

import br.dev.allissonnunes.algashop.product.catalog.application.PageModel;
import br.dev.allissonnunes.algashop.product.catalog.application.category.management.CategoryInput;
import br.dev.allissonnunes.algashop.product.catalog.application.category.management.CategoryManagementApplicationService;
import br.dev.allissonnunes.algashop.product.catalog.application.category.query.CategoryDetailOutput;
import br.dev.allissonnunes.algashop.product.catalog.application.category.query.CategoryFilter;
import br.dev.allissonnunes.algashop.product.catalog.application.category.query.CategoryQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
class CategoryController {

    private final CategoryQueryService categoryQueryService;

    private final CategoryManagementApplicationService categoryManagementApplicationService;

    @GetMapping
    ResponseEntity<PageModel<CategoryDetailOutput>> findCategories(final CategoryFilter filter, final WebRequest request) {
        if (!filter.isCacheable()) {
            return ResponseEntity.ok(categoryQueryService.filter(filter));
        }

        final Instant lastModified = categoryQueryService.lastModifiedAt();

        if (request.checkNotModified(lastModified.toEpochMilli())) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).lastModified(lastModified).build();
        }

        final PageModel<CategoryDetailOutput> result = categoryQueryService.filter(filter);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(Duration.ofMinutes(5)).cachePublic())
                .lastModified(lastModified)
                .body(result);
    }

    @PostMapping
    ResponseEntity<CategoryDetailOutput> createCategory(@RequestBody final @Valid CategoryInput input) {
        final UUID categoryId = categoryManagementApplicationService.create(input);
        final CategoryDetailOutput categoryDetailOutput = categoryQueryService.findById(categoryId);

        final var location = fromCurrentRequestUri().path("/{categoryId}")
                .buildAndExpand(categoryDetailOutput.id())
                .toUri();

        return ResponseEntity.created(location).body(categoryDetailOutput);
    }

    @GetMapping("/{categoryId}")
    ResponseEntity<CategoryDetailOutput> findCategory(@PathVariable final UUID categoryId) {
        final CategoryDetailOutput categoryDetailOutput = categoryQueryService.findById(categoryId);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(Duration.ofMinutes(1L)).cachePublic())
                .eTag("category:id:" + categoryDetailOutput.id() + ":v:" + categoryDetailOutput.version())
                .lastModified(categoryDetailOutput.lastModifiedAt())
                .body(categoryDetailOutput);
    }

    @PutMapping("/{categoryId}")
    ResponseEntity<CategoryDetailOutput> updateCategory(@PathVariable final UUID categoryId, @RequestBody final @Valid CategoryInput input) {
        categoryManagementApplicationService.update(categoryId, input);
        final CategoryDetailOutput categoryDetailOutput = categoryQueryService.findById(categoryId);
        return ResponseEntity.ok(categoryDetailOutput);
    }

    @DeleteMapping("/{categoryId}")
    ResponseEntity<Void> deleteCategory(@PathVariable final UUID categoryId) {
        categoryManagementApplicationService.disable(categoryId);
        return ResponseEntity.noContent().build();
    }

}
