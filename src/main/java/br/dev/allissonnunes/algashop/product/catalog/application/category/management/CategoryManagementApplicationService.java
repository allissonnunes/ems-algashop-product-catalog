package br.dev.allissonnunes.algashop.product.catalog.application.category.management;

import br.dev.allissonnunes.algashop.product.catalog.domain.model.category.Category;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.category.CategoryNotFoundException;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryManagementApplicationService {

    private final CategoryRepository categoryRepository;

    public UUID create(final CategoryInput input) {
        final Category category = new Category(input.name(), input.enabled());
        categoryRepository.save(category);
        return category.getId();
    }

    public void update(final UUID categoryId, final CategoryInput input) {
        final Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        category.setName(input.name());
        category.setEnabled(input.enabled());

        categoryRepository.save(category);
    }

    public void disable(final UUID categoryId) {
        final Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        category.setEnabled(false);

        categoryRepository.save(category);
    }

}
