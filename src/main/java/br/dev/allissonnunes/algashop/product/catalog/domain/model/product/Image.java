package br.dev.allissonnunes.algashop.product.catalog.domain.model.product;

import br.dev.allissonnunes.algashop.product.catalog.domain.model.IdGenerator;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Image {

    @EqualsAndHashCode.Include
    private UUID id;

    private String name;

    Image(final String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Image name cannot be blank");
        }

        this.id = IdGenerator.generateTimeBasedUUID();
        this.name = name;
    }

}
