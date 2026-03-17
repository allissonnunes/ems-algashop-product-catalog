package br.dev.allissonnunes.algashop.product.catalog.infrastructure.persistence.product;

import br.dev.allissonnunes.algashop.product.catalog.application.PageModel;
import br.dev.allissonnunes.algashop.product.catalog.application.product.query.ProductDetailOutput;
import br.dev.allissonnunes.algashop.product.catalog.application.product.query.ProductFilter;
import br.dev.allissonnunes.algashop.product.catalog.application.product.query.ProductQueryService;
import br.dev.allissonnunes.algashop.product.catalog.application.product.query.ProductSummaryOutput;
import br.dev.allissonnunes.algashop.product.catalog.application.utility.Mapper;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.product.Product;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.product.ProductNotFoundException;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.AggregationExpressionCriteria;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
class ProductQueryServiceImpl implements ProductQueryService {

    private static final String findWordRegex = "(?i)(?<= |^)%s(?= |$)"; //%s é do java
//    private static final String findWordRegex = "(?i)%s"; //%s é do java

    private final ProductRepository productRepository;

    private final Mapper mapper;

    private final MongoOperations mongoOperations;

    @Override
    public ProductDetailOutput findById(final UUID productId) {
        return productRepository.findById(productId)
                .map(p -> mapper.map(p, ProductDetailOutput.class))
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    @Override
    public PageModel<ProductSummaryOutput> filter(final ProductFilter filter) {
        final Query query = queryWith(filter);
        final Sort sort = sortWith(filter);
        final PageRequest pageRequest = PageRequest.of(filter.getPage(), filter.getSize(), sort);
        final Query paginatedQuery = query.with(pageRequest);

        final long totalElements = mongoOperations.count(query, Product.class);
        if (totalElements == 0) {
            return PageModel.empty(pageRequest.getPageSize());
        }

        final List<Product> filteredProducts = mongoOperations.find(paginatedQuery, Product.class);
        final int totalPages = (int) Math.ceil((double) totalElements / pageRequest.getPageSize());

        final List<ProductSummaryOutput> productSummaryPage = filteredProducts.stream()
                .map(p -> mapper.map(p, ProductSummaryOutput.class))
                .toList();

        return PageModel.<ProductSummaryOutput>builder()
                .content(productSummaryPage)
                .number(pageRequest.getPageNumber())
                .size(pageRequest.getPageSize())
                .totalPages(totalPages)
                .totalElements(totalElements)
                .build();
    }

    private Query queryWith(final ProductFilter filter) {
        final Query query = new Query();

        if (StringUtils.isNotBlank(filter.getTerm())) {
            final Pattern searchTermRegex = Pattern.compile(findWordRegex.formatted(filter.getTerm()));
            query.addCriteria(
                    TextCriteria.forDefaultLanguage().matching(filter.getTerm())
            );
        }

        if (filter.getHasDiscount() != null) {
            if (filter.getHasDiscount()) {
                query.addCriteria(
                        AggregationExpressionCriteria.whereExpr(
                                ComparisonOperators.valueOf("$salePrice")
                                        .lessThan("$regularPrice")
                        )
                );
            } else {
                query.addCriteria(
                        AggregationExpressionCriteria.whereExpr(
                                ComparisonOperators.valueOf("$salePrice")
                                        .equalTo("$regularPrice")
                        )
                );
            }
        }

        if (filter.getEnabled() != null) {
            query.addCriteria(Criteria.where("enabled").is(filter.getEnabled()));
        }

        if (filter.getInStock() != null) {
            if (filter.getInStock()) {
                query.addCriteria(Criteria.where("quantityInStock").gt(0));
            } else {
                query.addCriteria(Criteria.where("quantityInStock").is(0));
            }
        }

        if (filter.getPriceFrom() != null && filter.getPriceTo() != null) {
            query.addCriteria(
                    Criteria.where("salePrice")
                            .gte(filter.getPriceFrom())
                            .lte(filter.getPriceTo())
            );
        } else {
            if (filter.getPriceFrom() != null) {
                query.addCriteria(Criteria.where("salePrice").gte(filter.getPriceFrom()));
            }

            if (filter.getPriceTo() != null) {
                query.addCriteria(Criteria.where("salePrice").lte(filter.getPriceTo()));
            }
        }

        if (filter.getCategoriesId() != null && filter.getCategoriesId().length > 0) {
            query.addCriteria(Criteria.where("categoryId").in((Object[]) filter.getCategoriesId()));
        }

        if (filter.getAddedAtFrom() != null && filter.getAddedAtTo() != null) {
            query.addCriteria(
                    Criteria.where("createdAt")
                            .gte(filter.getAddedAtFrom())
                            .lte(filter.getAddedAtTo())
            );
        } else {
            if (filter.getAddedAtFrom() != null) {
                query.addCriteria(Criteria.where("createdAt").gte(filter.getAddedAtFrom()));
            }

            if (filter.getAddedAtTo() != null) {
                query.addCriteria(Criteria.where("createdAt").lte(filter.getAddedAtTo()));
            }
        }

        return query;
    }

    private Sort sortWith(final ProductFilter filter) {
        if (StringUtils.isNotBlank(filter.getTerm())) {
            return Sort.by("score");
        }
        return Sort.by(filter.getSortDirectionOrDefault(), filter.getSortByPropertyOrDefault().getProperty());
    }

}
