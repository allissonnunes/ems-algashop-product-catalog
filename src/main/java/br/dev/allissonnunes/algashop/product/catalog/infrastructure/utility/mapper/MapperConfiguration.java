package br.dev.allissonnunes.algashop.product.catalog.infrastructure.utility.mapper;

import br.dev.allissonnunes.algashop.product.catalog.application.utility.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;

@Configuration
class MapperConfiguration {

    @Bean
    Mapper mapper(final ConversionService conversionService) {
        return conversionService::convert;
    }

}
