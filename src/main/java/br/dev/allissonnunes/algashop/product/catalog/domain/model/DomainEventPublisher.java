package br.dev.allissonnunes.algashop.product.catalog.domain.model;

@FunctionalInterface
public interface DomainEventPublisher {

    void publish(Object event);

}
