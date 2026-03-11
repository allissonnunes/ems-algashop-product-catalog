package br.dev.allissonnunes.algashop.product.catalog.infrastructure.utility.mapper.mapstruct;

import br.dev.allissonnunes.algashop.product.catalog.application.category.query.CategoryDetailOutput;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.category.Category;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper
public interface CategoryMapper extends Converter<Category, CategoryDetailOutput> {

    @Override
    CategoryDetailOutput convert(Category source);

}
