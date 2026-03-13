package br.dev.allissonnunes.algashop.product.catalog.infrastructure.persistence.product;

import br.dev.allissonnunes.algashop.product.catalog.application.PageModel;
import br.dev.allissonnunes.algashop.product.catalog.application.product.query.ProductDetailOutput;
import br.dev.allissonnunes.algashop.product.catalog.application.product.query.ProductQueryService;
import br.dev.allissonnunes.algashop.product.catalog.application.product.query.ProductSummaryOutput;
import br.dev.allissonnunes.algashop.product.catalog.application.utility.Mapper;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.product.ProductNotFoundException;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductRepository productRepository;

    private final Mapper mapper;

    @Override
    public ProductDetailOutput findById(final UUID productId) {
        return productRepository.findById(productId)
                .map(p -> mapper.map(p, ProductDetailOutput.class))
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    @Override
    public PageModel<ProductSummaryOutput> filter(final Integer page, final Integer size) {
        throw new UnsupportedOperationException("Method not implemented yet");
    }

}
