package br.dev.allissonnunes.algashop.product.catalog.infrastructure.utility.mapper.mapstruct;

import br.dev.allissonnunes.algashop.product.catalog.infrastructure.utility.Slugfier;
import br.dev.allissonnunes.algashop.product.catalog.infrastructure.utility.mapper.ApplicationMappingProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class Tools {

    private final ApplicationMappingProperties mappingProperties;

    @Named("shortDescription")
    public String shortDescription(final String description) {
        if (StringUtils.isBlank(description)) {
            return null;
        }
        return StringUtils.abbreviate(description, 50);
    }

    @Named("slugify")
    public String slugify(final String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        return Slugfier.slugify(text);
    }

    @Named("imageUrl")
    public String imageUrl(final String imageName) {
        return mappingProperties.getImageStorageUrl() + "/" + imageName;
    }

}
