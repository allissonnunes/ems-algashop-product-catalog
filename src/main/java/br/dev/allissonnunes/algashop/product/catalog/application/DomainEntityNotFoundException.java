package br.dev.allissonnunes.algashop.product.catalog.application;

public class DomainEntityNotFoundException extends RuntimeException {

    public DomainEntityNotFoundException(String message) {
        this(message, null);
    }

    public DomainEntityNotFoundException(String message, Throwable cause) {
        super(message, cause, true, false);
    }

}
