package br.dev.allissonnunes.algashop.product.catalog.presentation;

import br.dev.allissonnunes.algashop.product.catalog.ContractBaseExtension;
import br.dev.allissonnunes.algashop.product.catalog.application.DomainEntityNotFoundException;
import br.dev.allissonnunes.algashop.product.catalog.application.PageModel;
import br.dev.allissonnunes.algashop.product.catalog.application.product.management.ProductInput;
import br.dev.allissonnunes.algashop.product.catalog.application.product.management.ProductManagementApplicationService;
import br.dev.allissonnunes.algashop.product.catalog.application.product.query.ProductDetailOutput;
import br.dev.allissonnunes.algashop.product.catalog.application.product.query.ProductDetailOutputTestDataBuilder;
import br.dev.allissonnunes.algashop.product.catalog.application.product.query.ProductQueryService;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.stubbing.Answer;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(ProductController.class)
@ExtendWith(ContractBaseExtension.class)
abstract class ProductBase {

    @MockitoBean
    private ProductQueryService productQueryService;

    @MockitoBean
    private ProductManagementApplicationService productManagementApplicationService;

    @BeforeEach
    void setUp() {
        final UUID validProductId = UUID.fromString("019bb3a0-5c32-7685-b712-9dd8373525d3");
        when(productQueryService.findById(eq(validProductId)))
                .thenReturn(ProductDetailOutputTestDataBuilder.aProduct().id(validProductId).build());

        when(productQueryService.filter(anyInt(), anyInt()))
                .thenAnswer(filterProductsAnswer());

        when(productManagementApplicationService.create(any(ProductInput.class)))
                .thenReturn(validProductId);

        final UUID invalidProductId = UUID.fromString("019bd762-8fed-7119-89b3-ae8c733727e8");
        when(productQueryService.findById(invalidProductId))
                .thenThrow(new DomainEntityNotFoundException("Product %s not found".formatted(invalidProductId)));
    }

    private @NonNull Answer<Object> filterProductsAnswer() {
        return invocation -> {
            int page = invocation.getArgument(0);
            int size = invocation.getArgument(1);

            return PageModel.<ProductDetailOutput>builder()
                    .content(List.of(
                            ProductDetailOutputTestDataBuilder.aProduct().build(),
                            ProductDetailOutputTestDataBuilder.aProductAlt1().build()
                    ))
                    .number(page)
                    .size(size)
                    .totalPages(1)
                    .totalElements(2L)
                    .build();
        };
    }

}