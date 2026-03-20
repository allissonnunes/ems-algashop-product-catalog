package br.dev.allissonnunes.algashop.product.catalog.domain.model.product;

import br.dev.allissonnunes.algashop.product.catalog.domain.model.DomainException;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.IdGenerator;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.category.Category;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.*;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@CompoundIndex(
        name = "pidx_product_by_category_enabledTrue_salePrice",
        def = "{'category._id': 1, 'salePrice': 1}",
        partialFilter = "{'enabled': true}"
)
@CompoundIndex(
        name = "pidx_product_by_category_enabledTrue_addedAt",
        def = "{'category._id': 1, 'addedAt': -1}",
        partialFilter = "{'enabled': true}"
)
@Document(collection = "products")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends AbstractAggregateRoot<Product> {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    @TextIndexed(weight = 1.0F)
    private String name;

    @Indexed(name = "idx_product_by_brand")
    private String brand;

    @TextIndexed(weight = 5.0F)
    private String description;

    private Integer quantityInStock;

    private Integer discountPercentageRounded;

    private BigDecimal regularPrice;

    private BigDecimal salePrice;

    private Boolean enabled;

    private ProductCategory category;

    @Version
    private Long version;

    @CreatedBy
    private UUID createdBy;

    @CreatedDate
    private OffsetDateTime createdAt;

    @LastModifiedBy
    private UUID lastModifiedBy;

    @LastModifiedDate
    private OffsetDateTime lastModifiedAt;

    @Builder
    public Product(final String name,
                   final String brand,
                   final String description,
                   final BigDecimal regularPrice,
                   final BigDecimal salePrice,
                   final Boolean enabled,
                   final Category category) {
        this.setId(IdGenerator.generateTimeBasedUUID());
        this.setName(name);
        this.setBrand(brand);
        this.setDescription(description);
        this.setQuantityInStock(0);
        this.setDiscountPercentageRounded(0);
        this.setRegularPrice(regularPrice);
        this.setSalePrice(salePrice);
        this.setEnabled(enabled);
        this.setCategory(category);
    }

    public void setName(final String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Product name cannot be blank");
        }
        this.name = name;
    }

    public void setBrand(final String brand) {
        if (StringUtils.isBlank(brand)) {
            throw new IllegalArgumentException("Product brand cannot be blank");
        }
        this.brand = brand;
    }

    public void changePrice(final BigDecimal regularPrice, final BigDecimal salePrice) {
        requireNonNull(regularPrice, "Product regular price cannot be null");
        requireNonNull(salePrice, "Product sale price cannot be null");

        final BigDecimal oldRegularPrice = this.regularPrice;
        final BigDecimal oldSalePrice = this.salePrice;
        final boolean wasOnSale = hasDiscount();

        if (regularPrice.compareTo(salePrice) < 0) {
            throw new DomainException("Sale price cannot be greater than regular price");
        }

        this.setRegularPrice(regularPrice);
        this.setSalePrice(salePrice);

        if (pricesDidNotChange(oldRegularPrice, oldSalePrice)) {
            return;
        }

        registerPriceChangedEvent(oldRegularPrice, oldSalePrice);

        if (isNewlyOnSale(wasOnSale)) {
            registerProductOnSaleEvent();
        }
    }

    public void setCategory(final Category category) {
        requireNonNull(category, "Product category cannot be null");
        this.category = ProductCategory.of(category);
    }

    public void disable() {
        this.setEnabled(false);
    }

    public void enable() {
        this.setEnabled(true);
    }

    public boolean isInStock() {
        return this.getQuantityInStock() != null && this.getQuantityInStock() > 0;
    }

    public boolean hasDiscount() {
        return this.getDiscountPercentageRounded() != null && this.getDiscountPercentageRounded() > 0;
    }

    private void setId(final UUID id) {
        this.id = requireNonNull(id, "Product id cannot be null");
    }

    private void setQuantityInStock(final Integer quantityInStock) {
        requireNonNull(quantityInStock, "Product quantity in stock cannot be null");
        if (quantityInStock < 0) {
            throw new IllegalArgumentException("Product quantity in stock cannot be negative");
        }
        this.quantityInStock = quantityInStock;
    }

    private void setRegularPrice(final BigDecimal regularPrice) {
        requireNonNull(regularPrice, "Product regular price cannot be null");
        if (regularPrice.signum() == -1) {
            throw new IllegalArgumentException("Product regular price cannot be negative");
        }
        this.regularPrice = regularPrice;
        this.calculateDiscountPercentage();
    }

    private void setSalePrice(final BigDecimal salePrice) {
        requireNonNull(salePrice, "Product sale price cannot be null");
        if (salePrice.signum() == -1) {
            throw new IllegalArgumentException("Product sale price cannot be negative");
        }
        this.salePrice = salePrice;
        this.calculateDiscountPercentage();
    }

    private boolean pricesDidNotChange(final BigDecimal oldRegularPrice, final BigDecimal oldSalePrice) {
        return Objects.equals(oldRegularPrice, this.regularPrice)
                && Objects.equals(oldSalePrice, this.salePrice);
    }

    private void registerPriceChangedEvent(final BigDecimal oldRegularPrice, final BigDecimal oldSalePrice) {
        super.registerEvent(
                new ProductPriceChangedEvent(
                        this.getId(),
                        oldRegularPrice,
                        oldSalePrice,
                        this.regularPrice,
                        this.salePrice,
                        Instant.now()
                )
        );
    }

    private boolean isNewlyOnSale(final boolean wasOnSale) {
        return hasDiscount() && !wasOnSale;
    }

    private void registerProductOnSaleEvent() {
        super.registerEvent(
                new ProductPlacedOnSaleEvent(
                        this.getId(),
                        this.regularPrice,
                        this.salePrice,
                        Instant.now()
                )
        );
    }

    private void setEnabled(final Boolean enabled) {
        this.enabled = requireNonNull(enabled, "Product enabled cannot be null");
    }

    private void calculateDiscountPercentage() {
        if (this.salePrice == null || this.regularPrice == null || this.regularPrice.signum() == 0) {
            this.discountPercentageRounded = 0;
            return;
        }

        this.discountPercentageRounded = BigDecimal.ONE
                .subtract(this.salePrice.divide(this.regularPrice, 4, RoundingMode.HALF_UP))
                .multiply(new BigDecimal(100))
                .setScale(0, RoundingMode.HALF_UP)
                .intValue();
    }

}
