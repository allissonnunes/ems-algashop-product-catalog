package br.dev.allissonnunes.algashop.product.catalog.infrastructure.persistence.category;

import br.dev.allissonnunes.algashop.product.catalog.application.PageModel;
import br.dev.allissonnunes.algashop.product.catalog.application.category.query.CategoryDetailOutput;
import br.dev.allissonnunes.algashop.product.catalog.application.category.query.CategoryFilter;
import br.dev.allissonnunes.algashop.product.catalog.application.category.query.CategoryQueryService;
import br.dev.allissonnunes.algashop.product.catalog.application.utility.Mapper;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.category.Category;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.category.CategoryNotFoundException;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
class CategoryQueryServiceImpl implements CategoryQueryService {

    private static final String findWordRegex = "(?i)(?<= |^)%s(?= |$)"; //%s é do java

    private final CategoryRepository categoryRepository;

    private final Mapper mapper;

    private final MongoOperations mongoOperations;

    @Cacheable(cacheNames = "algashop:categories:v1", key = "#categoryId")
    @Override
    public CategoryDetailOutput findById(final UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .map(c -> mapper.map(c, CategoryDetailOutput.class))
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }

    @Cacheable(cacheNames = "algashop:categories-filter:v1", key = "'default'", condition = "#filter.isCacheable()")
    @Override
    public PageModel<CategoryDetailOutput> filter(final CategoryFilter filter) {
        final Query query = queryWith(filter);
        final Sort sort = sortWith(filter);
        final PageRequest pageRequest = PageRequest.of(filter.getPage(), filter.getSize(), sort);
        final Query paginatedQuery = query.with(pageRequest);

        final long totalElements = mongoOperations.count(query, Category.class);
        if (totalElements == 0) {
            return PageModel.empty(pageRequest.getPageSize());
        }

        final List<Category> filteredCategories = mongoOperations.find(paginatedQuery, Category.class);
        final int totalPages = (int) Math.ceil((double) totalElements / pageRequest.getPageSize());

        final List<CategoryDetailOutput> categoryDetailsOutput = filteredCategories.stream()
                .map(p -> mapper.map(p, CategoryDetailOutput.class))
                .toList();

        return PageModel.<CategoryDetailOutput>builder()
                .content(categoryDetailsOutput)
                .number(pageRequest.getPageNumber())
                .size(pageRequest.getPageSize())
                .totalPages(totalPages)
                .totalElements(totalElements)
                .build();
    }

    @Override
    public Instant lastModifiedAt() {
        final Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("enabled").is(true)),
                Aggregation.group().max("lastModifiedAt").as("lastModifiedAt"),
                Aggregation.project("lastModifiedAt")
        );

        final AggregationResults<Document> result = mongoOperations
                .aggregate(aggregation, Category.class, Document.class);

        final Document lastModifiedDocument = result.getUniqueMappedResult();

        if (lastModifiedDocument != null) {
            return lastModifiedDocument.getDate("lastModifiedAt").toInstant();
        }

        return Instant.now();
    }

    private Query queryWith(final CategoryFilter filter) {
        final Query query = new Query();

        if (StringUtils.isNotBlank(filter.getName())) {
            final Pattern searchTermRegex = Pattern.compile(findWordRegex.formatted(filter.getName()));
            query.addCriteria(
                    Criteria.where("name").regex(searchTermRegex)
            );
        }

        if (filter.getEnabled() != null) {
            query.addCriteria(Criteria.where("enabled").is(filter.getEnabled()));
        }

        return query;
    }

    private Sort sortWith(final CategoryFilter filter) {
        return Sort.by(filter.getSortDirectionOrDefault(), filter.getSortByPropertyOrDefault().getProperty());
    }

}
