package br.dev.allissonnunes.algashop.product.catalog.infrastructure.persistence.product;

import br.dev.allissonnunes.algashop.product.catalog.application.PageModel;
import br.dev.allissonnunes.algashop.product.catalog.application.product.query.ProductDetailOutput;
import br.dev.allissonnunes.algashop.product.catalog.application.product.query.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class ProductQueryServiceImpl implements ProductQueryService {

    @Override
    public ProductDetailOutput findById(final UUID productId) {
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    @Override
    public PageModel<ProductDetailOutput> filter(final Integer page, final Integer size) {
        throw new UnsupportedOperationException("Method not implemented yet");
    }

}
