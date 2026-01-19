package br.dev.allissonnunes.algashop.product.catalog.application.product.management;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductManagementApplicationService {

    public UUID create(final ProductInput input) {
        return UUID.randomUUID();
    }

    public void update(final UUID productId, final ProductInput input) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (input == null) {
            throw new IllegalArgumentException("Product input cannot be null");
        }
    }

    public void disable(final UUID productId) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
    }

}
