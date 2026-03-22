package br.dev.allissonnunes.algashop.product.catalog.infrastructure.listener.product;

import br.dev.allissonnunes.algashop.product.catalog.domain.model.product.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class ProductEventListener {

    @EventListener
    public void on(final ProductPriceChangedEvent event) {
        log.info("ProductPriceChangedEvent - {}", event);
    }

    @EventListener
    public void on(final ProductPlacedOnSaleEvent event) {
        log.info("ProductPlacedOnSaleEvent - {}", event);
    }

    @EventListener
    public void on(final ProductAddedEvent event) {
        log.info("ProductAddedEvent - {}", event);
    }

    @EventListener
    public void on(final ProductListedEvent event) {
        log.info("ProductListedEvent - {}", event);
    }

    @EventListener
    public void on(final ProductDelistedEvent event) {
        log.info("ProductDelistedEvent - {}", event);
    }

    @EventListener
    public void on(final ProductRestockedEvent event) {
        log.info("ProductRestockedEvent - {}", event);
    }

    @EventListener
    public void on(final ProductSoldOutEvent event) {
        log.info("ProductSoldOutEvent - {}", event);
    }

}
