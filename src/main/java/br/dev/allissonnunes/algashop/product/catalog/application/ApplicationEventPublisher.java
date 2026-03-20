package br.dev.allissonnunes.algashop.product.catalog.application;

@FunctionalInterface
public interface ApplicationEventPublisher {

    void publish(Object event);

}
