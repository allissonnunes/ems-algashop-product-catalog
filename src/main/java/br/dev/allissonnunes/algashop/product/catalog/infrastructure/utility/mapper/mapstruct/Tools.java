package br.dev.allissonnunes.algashop.product.catalog.infrastructure.utility.mapper.mapstruct;

import br.dev.allissonnunes.algashop.product.catalog.infrastructure.utility.Slugfier;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public final class Tools {

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

}
