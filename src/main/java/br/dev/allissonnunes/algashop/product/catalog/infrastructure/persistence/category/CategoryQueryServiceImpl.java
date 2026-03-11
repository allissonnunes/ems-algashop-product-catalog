package br.dev.allissonnunes.algashop.product.catalog.infrastructure.persistence.category;

import br.dev.allissonnunes.algashop.product.catalog.application.PageModel;
import br.dev.allissonnunes.algashop.product.catalog.application.category.query.CategoryDetailOutput;
import br.dev.allissonnunes.algashop.product.catalog.application.category.query.CategoryQueryService;
import br.dev.allissonnunes.algashop.product.catalog.application.utility.Mapper;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.category.CategoryNotFoundException;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class CategoryQueryServiceImpl implements CategoryQueryService {

    private final CategoryRepository categoryRepository;

    private final Mapper mapper;

    @Override
    public CategoryDetailOutput findById(final UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .map(c -> mapper.map(c, CategoryDetailOutput.class))
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }

    @Override
    public PageModel<CategoryDetailOutput> filter(final Integer page, final Integer size) {
        return null;
    }

}
