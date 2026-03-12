package br.dev.allissonnunes.algashop.product.catalog.domain.model.product;

import br.dev.allissonnunes.algashop.product.catalog.domain.model.DomainException;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.IdGenerator;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Document(collection = "products")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    private String name;

    private String brand;

    private String description;

    private Integer quantityInStock;

    private BigDecimal regularPrice;

    private BigDecimal salePrice;

    private Boolean enabled;

    @Version
    private Long version;

    @CreatedBy
    private UUID createdBy;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedBy
    private UUID lastModifiedBy;

    @LastModifiedDate
    private Instant lastModifiedAt;

    @Builder
    public Product(final String name,
                   final String brand,
                   final String description,
                   final BigDecimal regularPrice,
                   final BigDecimal salePrice,
                   final Boolean enabled) {
        this.setId(IdGenerator.generateTimeBasedUUID());
        this.setName(name);
        this.setBrand(brand);
        this.setDescription(description);
        this.setQuantityInStock(0);
        this.setRegularPrice(regularPrice);
        this.setSalePrice(salePrice);
        this.setEnabled(enabled);
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

    public void setRegularPrice(final BigDecimal regularPrice) {
        requireNonNull(regularPrice, "Product regular price cannot be null");
        if (regularPrice.signum() == -1) {
            throw new IllegalArgumentException("Product regular price cannot be negative");
        }

        if (this.salePrice == null) {
            this.salePrice = regularPrice;
        } else if (regularPrice.compareTo(this.salePrice) < 0) {
//            throw new DomainException("Product regular price cannot be lower than sale price");
            throw new DomainException("Sale price cannot be greater than regular price");
        }
        this.regularPrice = regularPrice;
    }

    public void setSalePrice(final BigDecimal salePrice) {
        requireNonNull(salePrice, "Product sale price cannot be null");
        if (salePrice.signum() == -1) {
            throw new IllegalArgumentException("Product sale price cannot be negative");
        }

        if (this.regularPrice == null) {
            this.regularPrice = salePrice;
        } else if (this.regularPrice.compareTo(salePrice) < 0) {
            throw new DomainException("Sale price cannot be greater than regular price");
        }
        this.salePrice = salePrice;
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

    private void setEnabled(final Boolean enabled) {
        this.enabled = requireNonNull(enabled, "Product enabled cannot be null");
    }

}
