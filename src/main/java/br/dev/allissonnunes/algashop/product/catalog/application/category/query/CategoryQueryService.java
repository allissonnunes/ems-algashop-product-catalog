package br.dev.allissonnunes.algashop.product.catalog.application.category.query;

import br.dev.allissonnunes.algashop.product.catalog.application.PageModel;

import java.time.Instant;
import java.util.UUID;

public interface CategoryQueryService {

    CategoryDetailOutput findById(UUID categoryId);

    PageModel<CategoryDetailOutput> filter(CategoryFilter filter);

    Instant lastModifiedAt();

}
