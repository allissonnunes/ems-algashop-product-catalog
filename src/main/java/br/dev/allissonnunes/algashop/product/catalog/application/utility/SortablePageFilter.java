package br.dev.allissonnunes.algashop.product.catalog.application.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class SortablePageFilter<T> extends PageFilter {

    private T sortByProperty;

    private Sort.Direction sortDirection;

    public SortablePageFilter(final int page, final int size) {
        super(page, size);
    }

    protected abstract T getSortByPropertyOrDefault();

    protected abstract Sort.Direction getSortDirectionOrDefault();

}
