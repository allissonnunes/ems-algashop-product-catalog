package br.dev.allissonnunes.algashop.product.catalog.infrastructure.persistence.product;

import br.dev.allissonnunes.algashop.product.catalog.domain.model.NoStackTraceException;

public class StockUpdateFailedException extends NoStackTraceException {

    public StockUpdateFailedException(final String message) {
        super(message);
    }

}
