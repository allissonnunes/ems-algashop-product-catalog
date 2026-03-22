package br.dev.allissonnunes.algashop.product.catalog.domain.model.product;

import br.dev.allissonnunes.algashop.product.catalog.infrastructure.persistence.dataloader.DataLoader;
import br.dev.allissonnunes.algashop.product.catalog.infrastructure.persistence.dataloader.DataLoaderProperties;
import br.dev.allissonnunes.algashop.product.catalog.infrastructure.persistence.product.QuantityInStockAdjustmentMongoDB;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest(includeFilters = {
        @ComponentScan.Filter(
                type = FilterType.REGEX,
                pattern = "br.dev.allissonnunes.algashop.product.catalog.infrastructure.persistence.*(Configuration)"
        ),
        @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = { DataLoader.class, DataLoaderProperties.class, QuantityInStockAdjustmentMongoDB.class }
        )
})
@Slf4j
class QuantityInStockAdjustmentIT {

    @Autowired
    private QuantityInStockAdjustment quantityInStockAdjustment;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DataLoader dataLoader;

    private static final UUID EXISTING_PRODUCT_ID = UUID.fromString("946cea3b-d11d-4f11-b88d-3089b4e74087");

    @BeforeEach
    void setUp() throws Exception {
        dataLoader.run(new DefaultApplicationArguments());
    }

    @Test
    void shouldIncreaseQuantityInStock() {
        final Product product = productRepository.findById(EXISTING_PRODUCT_ID).orElseThrow();

        quantityInStockAdjustment.increase(EXISTING_PRODUCT_ID, 25);
        quantityInStockAdjustment.increase(EXISTING_PRODUCT_ID, 25);

        final Product updatedProduct = productRepository.findById(EXISTING_PRODUCT_ID).orElseThrow();

        assertThat(product.getQuantityInStock()).isEqualTo(50);
        assertThat(updatedProduct.getQuantityInStock()).isEqualTo(100);
    }

    @Test
    void shouldDecreaseQuantityInStock() {
        final Product product = productRepository.findById(EXISTING_PRODUCT_ID).orElseThrow();

        quantityInStockAdjustment.decrease(EXISTING_PRODUCT_ID, 25);
        quantityInStockAdjustment.decrease(EXISTING_PRODUCT_ID, -25);

        final Product updatedProduct = productRepository.findById(EXISTING_PRODUCT_ID).orElseThrow();

        assertThat(product.getQuantityInStock()).isEqualTo(50);
        assertThat(updatedProduct.getQuantityInStock()).isEqualTo(0);
    }

    @Test
    void shouldNotDecreaseQuantity() {
        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> quantityInStockAdjustment.decrease(EXISTING_PRODUCT_ID, 100));
        final Product product = productRepository.findById(EXISTING_PRODUCT_ID).orElseThrow();
        assertThat(product.getQuantityInStock()).isEqualTo(50);
    }

    @Test
    void shouldCalculateResult() {
        final Product product = productRepository.findById(EXISTING_PRODUCT_ID).orElseThrow();

        final QuantityInStockAdjustment.Result decreaseResult = quantityInStockAdjustment.decrease(EXISTING_PRODUCT_ID, 40);

        assertThat(decreaseResult.newQuantity()).isEqualTo(10);
        assertThat(decreaseResult.previousQuantity()).isEqualTo(50);

        final QuantityInStockAdjustment.Result increaseResult = quantityInStockAdjustment.increase(EXISTING_PRODUCT_ID, 40);

        assertThat(increaseResult.newQuantity()).isEqualTo(50);
        assertThat(increaseResult.previousQuantity()).isEqualTo(10);
    }

}