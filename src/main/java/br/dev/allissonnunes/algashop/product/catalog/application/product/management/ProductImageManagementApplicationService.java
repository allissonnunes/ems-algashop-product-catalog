package br.dev.allissonnunes.algashop.product.catalog.application.product.management;

import br.dev.allissonnunes.algashop.product.catalog.application.product.query.ImageOutput;
import br.dev.allissonnunes.algashop.product.catalog.application.storage.StorageProvider;
import br.dev.allissonnunes.algashop.product.catalog.application.utility.Mapper;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.DomainException;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.product.Image;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.product.Product;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.product.ProductNotFoundException;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class ProductImageManagementApplicationService {

    private final ProductRepository productRepository;

    private final StorageProvider storageProvider;

    private final Mapper mapper;

    @CacheEvict(cacheNames = "algashop:products:v1", key = "#productId")
    public ImageOutput create(final UUID productId, final ImageInput input) {
        requireNonNull(productId, "Product Id cannot be null");
        requireNonNull(input, "Image input cannot be null");

        final Product product = getProduct(productId);

        if (!this.storageProvider.fileExists(input.remoteFileName())) {
            throw new DomainException("Image %s was not found on storage provider".formatted(input.remoteFileName()));
        }

        if (this.productRepository.existsByImagesName(input.remoteFileName())) {
            throw new DomainException("Image %s already exists".formatted(input.remoteFileName()));
        }

        final UUID imageId = product.addImage(input.remoteFileName());

        this.productRepository.save(product);

        return mapper.map(product.getImageById(imageId), ImageOutput.class);
    }

    @CacheEvict(cacheNames = "algashop:products:v1", key = "#productId")
    public void delete(final UUID productId, final UUID imageId) {
        requireNonNull(productId, "Product Id cannot be null");
        requireNonNull(imageId, "Image Id cannot be null");

        final Product product = getProduct(productId);
        final Image image = product.getImageById(imageId);
        product.removeImage(imageId);
        this.storageProvider.deleteFile(image.getName());
        this.productRepository.save(product);
    }

    @CacheEvict(cacheNames = "algashop:products:v1", key = "#productId")
    public void primary(final UUID productId, final UUID imageId) {
        requireNonNull(productId, "Product Id cannot be null");
        requireNonNull(imageId, "Image Id cannot be null");
        final Product product = getProduct(productId);
        product.changeMainImage(imageId);
        this.productRepository.save(product);
    }

    private @NonNull Product getProduct(final UUID productId) {
        return this.productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

}
