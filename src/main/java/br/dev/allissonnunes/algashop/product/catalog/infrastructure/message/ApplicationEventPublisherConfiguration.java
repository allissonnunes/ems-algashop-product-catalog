package br.dev.allissonnunes.algashop.product.catalog.infrastructure.message;

import br.dev.allissonnunes.algashop.product.catalog.application.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ApplicationEventPublisherConfiguration {

    @Bean
    ApplicationEventPublisher applicationEventPublisher(
            final org.springframework.context.ApplicationEventPublisher springApplicationEventPublisher
    ) {
        return springApplicationEventPublisher::publishEvent;
    }

}
