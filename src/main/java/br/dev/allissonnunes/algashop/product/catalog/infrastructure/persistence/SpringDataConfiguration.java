package br.dev.allissonnunes.algashop.product.catalog.infrastructure.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@EnableMongoAuditing(
        auditorAwareRef = "auditorAware",
        dateTimeProviderRef = "dateTimeProvider"
)
@Configuration
class SpringDataConfiguration {

    @Bean
    DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    }

    @Bean
    AuditorAware<UUID> auditorAware() {
        return () -> Optional.of(UUID.randomUUID());
    }

}
