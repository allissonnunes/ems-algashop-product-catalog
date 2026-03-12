package br.dev.allissonnunes.algashop.product.catalog.infrastructure.utility.mapper.mapstruct;

import br.dev.allissonnunes.algashop.product.catalog.application.product.query.ProductDetailOutput;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper
public interface ProductMapper extends Converter<Product, ProductDetailOutput> {

    @Mapping(target = "addedAt", source = "createdAt")
    @Override
    ProductDetailOutput convert(Product source);

}
