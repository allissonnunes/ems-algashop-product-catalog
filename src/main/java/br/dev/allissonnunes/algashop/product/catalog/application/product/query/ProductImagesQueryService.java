package br.dev.allissonnunes.algashop.product.catalog.application.product.query;

import br.dev.allissonnunes.algashop.product.catalog.application.utility.Mapper;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.product.Product;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.product.ProductNotFoundException;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductImagesQueryService {

    private final ProductRepository productRepository;

    private final Mapper mapper;

    public List<ImageOutput> getAllImages(final UUID productId) {
        final Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        return product.getImages().stream()
                .map(i -> this.mapper.map(i, ImageOutput.class))
                .toList();
    }

    public ImageOutput getImage(final UUID productId, final UUID imageId) {
        final Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        return this.mapper.map(product.getImageById(imageId), ImageOutput.class);
    }

}
