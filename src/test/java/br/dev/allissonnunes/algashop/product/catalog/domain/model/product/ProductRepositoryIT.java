package br.dev.allissonnunes.algashop.product.catalog.domain.model.product;

import br.dev.allissonnunes.algashop.product.catalog.TestcontainersConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import({ TestcontainersConfiguration.class })
@DataMongoTest(includeFilters = {
        @ComponentScan.Filter(
                type = FilterType.REGEX,
                pattern = "br.dev.allissonnunes.algashop.product.catalog.infrastructure.persistence.*(Configuration)"
        )
})
@Slf4j
class ProductRepositoryIT {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldFilter() {
        List<Product> products = productRepository.findAllByEnabled(true);
        products.forEach(product -> log.info("Product - Id: {}, Name: {}", product.getId(), product.getName()));
    }

}