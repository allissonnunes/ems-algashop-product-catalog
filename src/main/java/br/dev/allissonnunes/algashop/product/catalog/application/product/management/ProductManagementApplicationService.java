package br.dev.allissonnunes.algashop.product.catalog.application.product.management;

import br.dev.allissonnunes.algashop.product.catalog.domain.model.category.Category;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.category.CategoryNotFoundException;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.category.CategoryRepository;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.product.Product;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.product.ProductNotFoundException;
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
        final Product product = createProduct(input);
        productRepository.save(product);
        return product.getId();
    }

    public void update(final UUID productId, final ProductInput input) {
        final Product product = findProduct(productId);
        updateProduct(product, input);
        productRepository.save(product);
    }

    public void disable(final UUID productId) {
        final Product product = findProduct(productId);
        product.disable();
        productRepository.save(product);
    }

    public void enable(final UUID productId) {
        final Product product = findProduct(productId);
        product.enable();
        productRepository.save(product);
    }

    private Product createProduct(final ProductInput input) {
        final Category category = findCategory(input.categoryId());

        return Product.builder()
                .name(input.name())
                .brand(input.brand())
                .description(input.description())
                .regularPrice(input.regularPrice())
                .salePrice(input.salePrice())
                .enabled(input.enabled())
                .category(category)
                .build();
    }

    private void updateProduct(final Product product, final ProductInput input) {
        final Category category = findCategory(input.categoryId());

        product.setName(input.name());
        product.setBrand(input.brand());
        product.setDescription(input.description());
        product.setCategory(category);

        product.changePrice(input.regularPrice(), input.salePrice());
    }

    private Category findCategory(final @NotNull UUID categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }

    private Product findProduct(final @NotNull UUID productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
    }

}
