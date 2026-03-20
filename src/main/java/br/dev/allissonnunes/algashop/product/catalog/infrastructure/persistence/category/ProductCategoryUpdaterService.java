package br.dev.allissonnunes.algashop.product.catalog.infrastructure.persistence.category;

import br.dev.allissonnunes.algashop.product.catalog.application.category.event.CategoryUpdatedEvent;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCategoryUpdaterService {

    private final MongoOperations mongoOperations;

    public void updateProductCategory(final CategoryUpdatedEvent event) {
        final Query query = new Query()
                .addCriteria(Criteria.where("category._id").is(event.id()));

        final Update update = new Update()
                .set("category.name", event.name())
                .set("category.enabled", event.enabled());

        mongoOperations.updateMulti(query, update, Product.class);
    }

}
