package br.dev.allissonnunes.algashop.product.catalog.infrastructure.persistence;

//import org.springframework.boot.mongodb.autoconfigure.MongoClientSettingsBuilderCustomizer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
//import java.util.concurrent.TimeUnit;

@Configuration
class MongoDBConfiguration {

//    @Bean
//    MongoClientSettingsBuilderCustomizer customizer() {
//        return settings -> settings
//                .applyToConnectionPoolSettings(builder ->
//                        builder
//                                .minSize(20)
//                                .maxSize(200)
//                                .maxWaitTime(500L, TimeUnit.MILLISECONDS)
//                                .maxConnectionLifeTime(30L, TimeUnit.MINUTES)
//                                .maxConnectionIdleTime(5L, TimeUnit.MINUTES)
//                )
//                .applyToSocketSettings(builder ->
//                        builder
//                                .connectTimeout(2L, TimeUnit.SECONDS)
//                                .readTimeout(3L, TimeUnit.SECONDS)
//                )
//                .applyToServerSettings(builder ->
//                        builder
//                                .heartbeatFrequency(2L, TimeUnit.SECONDS)
//                );
//    }

    @Bean
    MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(List.of(
                new OffsetDateTimeReadConverter(),
                new OffsetDateTimeWriteConverter()
        ));
    }

    @Bean
    MongoTransactionManager mongoTransactionManager(final MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTransactionManager(mongoDatabaseFactory);
    }

    @ReadingConverter
    public static class OffsetDateTimeReadConverter implements Converter<Date, OffsetDateTime> {

        @Override
        public OffsetDateTime convert(final Date source) {
            return OffsetDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault());
        }

    }

    @WritingConverter
    public static class OffsetDateTimeWriteConverter implements Converter<OffsetDateTime, Date> {

        @Override
        public Date convert(final OffsetDateTime source) {
            return Date.from(source.toInstant());
        }

    }

}
