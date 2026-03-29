package br.dev.allissonnunes.algashop.product.catalog.application.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PageFilter {

    private int page = 0;

    private int size = 15;

}
