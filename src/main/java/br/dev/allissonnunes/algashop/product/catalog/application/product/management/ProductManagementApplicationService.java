package br.dev.allissonnunes.algashop.product.catalog.application.product.management;

import br.dev.allissonnunes.algashop.product.catalog.application.product.query.ProductDetailOutput;
import br.dev.allissonnunes.algashop.product.catalog.application.utility.Mapper;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.category.Category;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.category.CategoryNotFoundException;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.category.CategoryRepository;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.product.*;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductManagementApplicationService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final StockMovementRepository stockMovementRepository;

    private final StockService stockService;

    private final Mapper mapper;

    @CachePut(cacheNames = "algashop:products:v1", key = "#result.id()", condition = "#input.enabled()")
    public ProductDetailOutput create(final ProductInput input) {
        final Product product = createProduct(input);
        productRepository.save(product);
        return mapper.map(product, ProductDetailOutput.class);
    }

    @CachePut(cacheNames = "algashop:products:v1", key = "#productId", condition = "#input.enabled() == true")
    @CacheEvict(cacheNames = "algashop:products:v1", key = "#productId", condition = "#input.enabled() == false")
    public ProductDetailOutput update(final UUID productId, final ProductInput input) {
        final Product product = findProduct(productId);
        updateProduct(product, input);
        productRepository.save(product);
        return mapper.map(product, ProductDetailOutput.class);
    }

    @CacheEvict(cacheNames = "algashop:products:v1", key = "#productId")
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

    @Transactional
    public void restock(final UUID productId, final int quantity) {
        final Product product = findProduct(productId);
        final StockMovement stockMovement = stockService.restock(product, quantity);
        stockMovementRepository.save(stockMovement);
    }

    @Transactional
    public void withdraw(final UUID productId, final int quantity) {
        final Product product = findProduct(productId);
        final StockMovement stockMovement = stockService.withdraw(product, quantity);
        stockMovementRepository.save(stockMovement);
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
