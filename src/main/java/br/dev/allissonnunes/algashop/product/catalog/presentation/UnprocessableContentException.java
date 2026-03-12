package br.dev.allissonnunes.algashop.product.catalog.presentation;

import br.dev.allissonnunes.algashop.product.catalog.domain.model.NoStackTraceException;

public class UnprocessableContentException extends NoStackTraceException {

    public UnprocessableContentException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
