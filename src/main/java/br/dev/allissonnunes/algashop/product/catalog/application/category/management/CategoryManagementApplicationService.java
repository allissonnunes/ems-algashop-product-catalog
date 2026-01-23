package br.dev.allissonnunes.algashop.product.catalog.application.category.management;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryManagementApplicationService {

    public UUID create(final CategoryInput input) {
        return UUID.randomUUID();
    }

    public UUID update(final UUID categoryId, final CategoryInput input) {
        return UUID.randomUUID();
    }

    public void delete(final UUID categoryId) {

    }

}
