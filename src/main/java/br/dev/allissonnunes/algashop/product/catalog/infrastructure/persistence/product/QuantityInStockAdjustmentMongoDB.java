package br.dev.allissonnunes.algashop.product.catalog.infrastructure.persistence.product;

import br.dev.allissonnunes.algashop.product.catalog.domain.model.product.Product;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.product.ProductNotFoundException;
import br.dev.allissonnunes.algashop.product.catalog.domain.model.product.QuantityInStockAdjustment;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class QuantityInStockAdjustmentMongoDB implements QuantityInStockAdjustment {

    private final MongoOperations mongoOperations;

    @Override
    public Result increase(final UUID productId, final int quantity) {
        final Query stockUpdateCriteria = new Query()
                .addCriteria(
                        Criteria.where("_id").is(productId)
                );

        return changeStockQuantity(productId, quantity, stockUpdateCriteria);
    }

    @Override
    public Result decrease(final UUID productId, final int quantity) {
        final Query stockUpdateCriteria = new Query()
                .addCriteria(
                        Criteria.where("_id")
                                .is(productId)
                                .and("quantityInStock").gte(quantity)
                );

        final int quantityAdjustment = Integer.signum(quantity) == -1 ? quantity : -quantity;
        return changeStockQuantity(productId, quantityAdjustment, stockUpdateCriteria);
    }

    private Result changeStockQuantity(final UUID productId, final int quantity, final Query stockUpdateCriteria) {
        final Aggregation findProductQuantityAggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("_id").is(productId)),
                Aggregation.project("quantityInStock")
        );

        final Document productQuantityBeforeUpdate = mongoOperations
                .aggregate(findProductQuantityAggregation, Product.class, Document.class)
                .getUniqueMappedResult();

        if (productQuantityBeforeUpdate == null) {
            throw new ProductNotFoundException(productId);
        }

        final Integer productQuantityBeforeUpdateValue = productQuantityBeforeUpdate.getInteger("quantityInStock");

        final Update updateOperation = new Update()
                .inc("quantityInStock", quantity)
                .inc("version", 1)
                .set("lastModifiedAt", OffsetDateTime.now());

        final Product updatedProduct = mongoOperations.findAndModify(
                stockUpdateCriteria,
                updateOperation,
                new FindAndModifyOptions().returnNew(true),
                Product.class);

        if (updatedProduct == null) {
            throw new StockUpdateFailedException("Failed to update stock of product %s".formatted(productId));
        }

        final Integer updatedProductQuantityInStock = updatedProduct.getQuantityInStock();

        return new Result(
                productId,
                productQuantityBeforeUpdateValue,
                updatedProductQuantityInStock
        );
    }

}
