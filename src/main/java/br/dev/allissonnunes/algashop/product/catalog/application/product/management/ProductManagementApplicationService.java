package br.dev.allissonnunes.algashop.product.catalog.application.product.management;

import br.dev.allissonnunes.algashop.product.catalog.domain.model.category.Category;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.category.CategoryNotFoundException;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.category.CategoryRepository;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.product.Product;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.product.ProductRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductManagementApplicationService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    public UUID create(final ProductInput input) {
        final Product product = mapToProduct(input);
        productRepository.save(product);
        return product.getId();
    }

    private Product mapToProduct(final ProductInput input) {
        final Category category = findCategory(input.categoryId());

        return Product.builder()
                .name(input.name())
                .brand(input.brand())
                .description(input.description())
                .regularPrice(input.regularPrice())
                .salePrice(input.salePrice())
                .enabled(input.enabled())
                .build();
    }

    public void update(UUID productId, ProductInput input) {

    }

    public void disable(UUID productId) {

    }

    private Category findCategory(final @NotNull UUID categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }

}
