package br.dev.allissonnunes.algashop.product.catalog.infrastructure.utility.mapper.mapstruct;

import br.dev.allissonnunes.algashop.product.catalog.application.product.query.ProductDetailOutput;
import br.dev.allissonnunes.algashop.product.catalog.application.product.query.ProductSummaryOutput;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

public interface ProductMapper {

    @Mapper(uses = { Tools.class })
    interface ToProductDetailOutput extends Converter<Product, ProductDetailOutput> {

        @Mapping(target = "addedAt", source = "createdAt")
        @Mapping(target = "hasDiscount", expression = "java(source.hasDiscount())")
        @Mapping(target = "slug", source = "name", qualifiedByName = "slugify")
        @Override
        ProductDetailOutput convert(Product source);

    }

    @Mapper(uses = { Tools.class })
    interface ToProductSummaryOutput extends Converter<Product, ProductSummaryOutput> {

        @Mapping(target = "addedAt", source = "createdAt")
        @Mapping(target = "hasDiscount", expression = "java(source.hasDiscount())")
        @Mapping(target = "slug", source = "name", qualifiedByName = "slugify")
        @Mapping(target = "shortDescription", source = "description", qualifiedByName = "shortDescription")
        @Override
        ProductSummaryOutput convert(Product source);

    }

}
