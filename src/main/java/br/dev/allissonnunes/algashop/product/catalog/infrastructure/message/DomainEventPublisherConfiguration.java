package br.dev.allissonnunes.algashop.product.catalog.infrastructure.message;

import br.dev.allissonnunes.algashop.product.catalog.domain.model.DomainEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DomainEventPublisherConfiguration {

    @Bean
    DomainEventPublisher domainEventPublisher(final ApplicationEventPublisher springApplicationEventPublisher) {
        return springApplicationEventPublisher::publishEvent;
    }

}
