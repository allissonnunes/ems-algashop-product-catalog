package br.dev.allissonnunes.algashop.product.catalog.domain.model.product;

import br.dev.allissonnunes.algashop.product.catalog.domain.model.DomainEventPublisher;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class StockService {

    private final QuantityInStockAdjustment quantityInStockAdjustment;

    private final DomainEventPublisher domainEventPublisher;

    public StockMovement restock(final Product product, final int quantity) {
        requireNonNull(product, "product cannot be null");
        if (quantity < 1) {
            throw new IllegalArgumentException("quantity must be positive");
        }

        final QuantityInStockAdjustment.Result increaseResult;
        try {
            increaseResult = quantityInStockAdjustment.increase(product.getId(), quantity);
        } catch (Exception e) {
            throw new DomainException("Failed to restock product %s".formatted(product.getId()), e);
        }

        if (increaseResult.isRestocked()) {
            domainEventPublisher.publish(new ProductRestockedEvent(this, product.getId()));
        }

        return StockMovement.builder()
                .productId(product.getId())
                .movementQuantity(quantity)
                .previousQuantity(increaseResult.previousQuantity())
                .newQuantity(increaseResult.newQuantity())
                .type(StockMovement.MovementType.STOCK_IN)
                .build();
    }

    public StockMovement withdraw(final Product product, final int quantity) {
        requireNonNull(product, "product cannot be null");
        if (quantity < 1) {
            throw new IllegalArgumentException("quantity must be positive");
        }

        final QuantityInStockAdjustment.Result decreaseResult;
        try {
            decreaseResult = quantityInStockAdjustment.decrease(product.getId(), quantity);
        } catch (Exception e) {
            throw new DomainException("Failed to withdraw product %s from stock".formatted(product.getId()), e);
        }

        if (decreaseResult.isOutOfStock()) {
            domainEventPublisher.publish(new ProductSoldOutEvent(this, product.getId()));
        }

        return StockMovement.builder()
                .productId(product.getId())
                .movementQuantity(quantity)
                .previousQuantity(decreaseResult.previousQuantity())
                .newQuantity(decreaseResult.newQuantity())
                .type(StockMovement.MovementType.STOCK_OUT)
                .build();
    }

}
