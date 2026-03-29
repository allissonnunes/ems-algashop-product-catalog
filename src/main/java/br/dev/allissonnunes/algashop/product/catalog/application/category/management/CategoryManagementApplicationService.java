package br.dev.allissonnunes.algashop.product.catalog.application.category.management;

import br.dev.allissonnunes.algashop.product.catalog.application.ApplicationEventPublisher;
import br.dev.allissonnunes.algashop.product.catalog.application.category.event.CategoryUpdatedEvent;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.category.Category;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.category.CategoryNotFoundException;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.category.CategoryRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryManagementApplicationService {

    private final CategoryRepository categoryRepository;

    private final ApplicationEventPublisher publisher;

    @CacheEvict(value = "algashop:categories-filter:v1", key = "'default'")
    public UUID create(final CategoryInput input) {
        final Category category = new Category(input.name(), input.enabled());
        categoryRepository.save(category);
        return category.getId();
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "algashop:categories-filter:v1", key = "'default'"),
                    @CacheEvict(value = "algashop:categories:v1", key = "#categoryId")
            }
    )
    public void update(final UUID categoryId, final CategoryInput input) {
        final Category category = findCategory(categoryId);

        category.setName(input.name());
        category.setEnabled(input.enabled());

        categoryRepository.save(category);

        publisher.publish(new CategoryUpdatedEvent(category.getId(), category.getName(), category.getEnabled()));
    }

    @CacheEvict(value = "algashop:categories:v1", key = "#categoryId")
    public void disable(final UUID categoryId) {
        final Category category = findCategory(categoryId);

        category.setEnabled(false);

        categoryRepository.save(category);

        publisher.publish(new CategoryUpdatedEvent(category.getId(), category.getName(), category.getEnabled()));
    }

    private Category findCategory(final @NotNull UUID categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }

}
