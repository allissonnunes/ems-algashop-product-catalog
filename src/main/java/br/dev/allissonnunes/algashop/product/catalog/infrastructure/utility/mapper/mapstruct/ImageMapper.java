package br.dev.allissonnunes.algashop.product.catalog.infrastructure.utility.mapper.mapstruct;

import br.dev.allissonnunes.algashop.product.catalog.application.product.query.ImageOutput;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.product.Image;
import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

public interface ImageMapper {

    @Mapper(uses = { Tools.class })
    interface ToImageOutput extends Converter<Image, ImageOutput> {

        @Mapping(target = "url", source = "name", qualifiedByName = "imageUrl")
        @Override
        @Nullable
        ImageOutput convert(@Nullable Image source);

    }

}
