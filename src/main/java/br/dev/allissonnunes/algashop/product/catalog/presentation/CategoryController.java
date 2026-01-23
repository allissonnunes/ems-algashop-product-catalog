package br.dev.allissonnunes.algashop.product.catalog.presentation;

import br.dev.allissonnunes.algashop.product.catalog.application.PageModel;
import br.dev.allissonnunes.algashop.product.catalog.application.category.management.CategoryInput;
import br.dev.allissonnunes.algashop.product.catalog.application.category.management.CategoryManagementApplicationService;
import br.dev.allissonnunes.algashop.product.catalog.application.category.query.CategoryDetailOutput;
import br.dev.allissonnunes.algashop.product.catalog.application.category.query.CategoryQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
class CategoryController {

    private final CategoryQueryService categoryQueryService;

    private final CategoryManagementApplicationService categoryManagementApplicationService;

    @GetMapping
    ResponseEntity<PageModel<CategoryDetailOutput>> findCategories(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size
    ) {
        return ResponseEntity.ok(categoryQueryService.filter(page, size));
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
        return ResponseEntity.ok(categoryQueryService.findById(categoryId));
    }

    @PutMapping("/{categoryId}")
    ResponseEntity<CategoryDetailOutput> updateCategory(@PathVariable final UUID categoryId, @RequestBody final @Valid CategoryInput input) {
        final UUID updatedCategoryId = categoryManagementApplicationService.update(categoryId, input);
        final CategoryDetailOutput categoryDetailOutput = categoryQueryService.findById(updatedCategoryId);
        return ResponseEntity.ok(categoryDetailOutput);
    }

    @DeleteMapping("/{categoryId}")
    ResponseEntity<Void> deleteCategory(@PathVariable final UUID categoryId) {
        categoryManagementApplicationService.delete(categoryId);
        return ResponseEntity.noContent().build();
    }

}
