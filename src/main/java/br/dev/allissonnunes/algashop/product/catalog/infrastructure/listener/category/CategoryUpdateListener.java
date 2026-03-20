package br.dev.allissonnunes.algashop.product.catalog.infrastructure.listener.category;

import br.dev.allissonnunes.algashop.product.catalog.application.category.event.CategoryUpdatedEvent;
import br.dev.allissonnunes.algashop.product.catalog.infrastructure.persistence.category.ProductCategoryUpdaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CategoryUpdateListener {

    private final ProductCategoryUpdaterService productCategoryUpdaterService;

    @Async
    @EventListener
    void on(final CategoryUpdatedEvent event) {
        productCategoryUpdaterService.updateProductCategory(event);
    }

}
