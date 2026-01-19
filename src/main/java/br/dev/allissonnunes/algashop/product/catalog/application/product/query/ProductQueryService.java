package br.dev.allissonnunes.algashop.product.catalog.application.product.query;

import br.dev.allissonnunes.algashop.product.catalog.presentation.PageModel;
import br.dev.allissonnunes.algashop.product.catalog.presentation.ProductDetailOutput;

import java.util.UUID;

public interface ProductQueryService {

    ProductDetailOutput findById(UUID productId);

    PageModel<ProductDetailOutput> filter(Integer page, Integer size);

}
