package br.dev.allissonnunes.algashop.product.catalog.presentation;

import br.dev.allissonnunes.algashop.product.catalog.ContractBaseExtension;
import br.dev.allissonnunes.algashop.product.catalog.application.PageModel;
import br.dev.allissonnunes.algashop.product.catalog.application.category.management.CategoryInput;
import br.dev.allissonnunes.algashop.product.catalog.application.category.management.CategoryManagementApplicationService;
import br.dev.allissonnunes.algashop.product.catalog.application.category.query.CategoryDetailOutputTestDataBuilder;
import br.dev.allissonnunes.algashop.product.catalog.application.category.query.CategoryFilter;
import br.dev.allissonnunes.algashop.product.catalog.application.category.query.CategoryQueryService;
import br.dev.allissonnunes.algashop.product.catalog.infrastructure.utility.mapper.mapstruct.Tools;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(
        controllers = CategoryController.class,
        includeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.REGEX,
                        pattern = "br.dev.allissonnunes.algashop.product.catalog.infrastructure.utility.*(Configuration)"
                ),
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = {
                                Tools.class
                        }
                )
        }
)
@ExtendWith(ContractBaseExtension.class)
abstract class CategoryBase {

    @MockitoBean
    private CategoryQueryService categoryQueryService;

    @MockitoBean
    private CategoryManagementApplicationService categoryManagementApplicationService;

    @BeforeEach
    void setUp() {
        when(categoryQueryService.filter(any(CategoryFilter.class)))
                .thenAnswer(invocation -> {
                    CategoryFilter filter = invocation.getArgument(0);
                    return new PageModel<>(List.of(
                            CategoryDetailOutputTestDataBuilder.aCategoryDetailOutput().build(),
                            CategoryDetailOutputTestDataBuilder.aCategoryDetailOutputAlt1().build()
                    ), filter.getPage(), filter.getSize(), 1, 2);
                });

        final UUID newCategoryId = UUID.randomUUID();
        when(categoryManagementApplicationService.create(any(CategoryInput.class)))
                .thenReturn(newCategoryId);
        when(categoryQueryService.findById(newCategoryId))
                .thenReturn(CategoryDetailOutputTestDataBuilder.aCategoryDetailOutput().id(newCategoryId).build());


        final UUID existentCategoryId = UUID.fromString("019bdbc2-037f-7983-9af2-76cbd2ab94c5");
        when(categoryQueryService.findById(existentCategoryId))
                .thenReturn(CategoryDetailOutputTestDataBuilder.aCategoryDetailOutput().id(existentCategoryId).build());

        final UUID updateCategoryId = UUID.fromString("019bebc3-a9c3-7fde-9e2c-c65015132616");
        doNothing().when(categoryManagementApplicationService).update(any(UUID.class), any(CategoryInput.class));
        when(categoryQueryService.findById(updateCategoryId))
                .thenReturn(CategoryDetailOutputTestDataBuilder.aCategoryDetailOutput()
                        .id(updateCategoryId)
                        .name("GPUs")
                        .build());

        doNothing().when(categoryManagementApplicationService).disable(any(UUID.class));
    }

}