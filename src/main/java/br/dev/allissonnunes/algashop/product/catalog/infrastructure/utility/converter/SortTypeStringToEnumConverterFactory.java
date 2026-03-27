package br.dev.allissonnunes.algashop.product.catalog.infrastructure.utility.converter;

import br.dev.allissonnunes.algashop.product.catalog.application.category.query.CategoryFilter;
import br.dev.allissonnunes.algashop.product.catalog.application.product.query.ProductFilter;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Set;

@Configuration
class SortTypeStringToEnumConverterFactoryConfiguration {

    @Bean
    WebMvcConfigurer sortTypeStringToEnumConverterFactoryFormatterRegistry() {
        return new WebMvcConfigurer() {
            @Override
            public void addFormatters(final @NonNull FormatterRegistry registry) {
                registry.addConverterFactory(new SortTypeStringToEnumConverterFactory());
            }
        };
    }

    static class SortTypeStringToEnumConverterFactory implements ConverterFactory<String, Enum<?>>, ConditionalConverter {

        private static final Set<Class<?>> AVAILABLE_SORT_TYPES = Set.of(
                CategoryFilter.SortType.class,
                ProductFilter.SortType.class
        );

        @Override
        public boolean matches(final @NonNull TypeDescriptor sourceType, final @NonNull TypeDescriptor targetType) {
            return AVAILABLE_SORT_TYPES.contains(targetType.getType());
        }

        @Override
        public <T extends Enum<?>> @NonNull Converter<String, ? extends @Nullable T> getConverter(final @NonNull Class<T> targetType) {
            return new SortTypeStringToEnum<>(targetType);
        }

        @RequiredArgsConstructor
        static class SortTypeStringToEnum<T extends Enum> implements Converter<String, @Nullable T> {

            private final Class<T> enumType;

            @Override
            public @Nullable T convert(String source) {
                if (source.isEmpty()) {
                    // It's an empty enum identifier: reset the enum value to null.
                    return null;
                }
                return (T) Enum.valueOf(this.enumType, source.trim().toUpperCase());
            }

        }

    }

}
