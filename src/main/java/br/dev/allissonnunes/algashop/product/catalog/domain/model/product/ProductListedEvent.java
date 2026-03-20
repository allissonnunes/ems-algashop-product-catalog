package br.dev.allissonnunes.algashop.product.catalog.domain.model.product;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ProductListedEvent extends ApplicationEvent {

    private final UUID productId;

    public ProductListedEvent(final Object source, final UUID productId) {
        super(source);
        this.productId = productId;
    }

}
