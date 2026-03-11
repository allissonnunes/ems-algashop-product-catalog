package br.dev.allissonnunes.algashop.product.catalog.application.utility;

import org.jspecify.annotations.Nullable;

public interface Mapper {

    <T> @Nullable T map(@Nullable Object source, Class<T> targetType);

}
