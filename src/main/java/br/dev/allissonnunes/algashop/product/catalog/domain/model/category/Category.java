package br.dev.allissonnunes.algashop.product.catalog.domain.model.category;

import br.dev.allissonnunes.algashop.product.catalog.domain.model.IdGenerator;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Document(collection = "categories")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    private String name;

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

    public Category(final String name, final Boolean enabled) {
        this.setId(IdGenerator.generateTimeBasedUUID());
        this.setName(name);
        this.setEnabled(enabled);
    }

    public void setName(final String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Category name cannot be blank");
        }
        this.name = name;
    }

    public void setEnabled(final Boolean enabled) {
        this.enabled = requireNonNull(enabled, "Category enabled cannot be null");
    }

}
