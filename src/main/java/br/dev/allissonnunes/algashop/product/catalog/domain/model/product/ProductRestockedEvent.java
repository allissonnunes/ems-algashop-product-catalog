package br.dev.allissonnunes.algashop.product.catalog.domain.model.product;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public final class ProductRestockedEvent extends ApplicationEvent {

    private final UUID productId;

    public ProductRestockedEvent(final Object source, final UUID productId) {
        super(source);
        this.productId = productId;
    }

}
