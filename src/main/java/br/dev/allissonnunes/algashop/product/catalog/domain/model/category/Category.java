package br.dev.allissonnunes.algashop.product.catalog.domain.model.category;

import br.dev.allissonnunes.algashop.product.catalog.domain.model.IdGenerator;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
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

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    public Category(final String name, final Boolean enabled) {
        this.setId(IdGenerator.generateTimeBasedUUID());
        this.setName(name);
        this.setEnabled(enabled);
        this.setCreatedAt(OffsetDateTime.now());
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
