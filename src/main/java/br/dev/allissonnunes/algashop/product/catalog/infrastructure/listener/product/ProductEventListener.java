package br.dev.allissonnunes.algashop.product.catalog.infrastructure.listener.product;

import br.dev.allissonnunes.algashop.product.catalog.domain.model.product.ProductPlacedOnSaleEvent;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.product.ProductPriceChangedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class ProductEventListener {

    @EventListener
    void on(final ProductPriceChangedEvent event) {
        log.info("ProductPriceChangedEvent - {}", event);
    }

    @EventListener
    void on(final ProductPlacedOnSaleEvent event) {
        log.info("ProductPlacedOnSaleEvent - {}", event);
    }

}
