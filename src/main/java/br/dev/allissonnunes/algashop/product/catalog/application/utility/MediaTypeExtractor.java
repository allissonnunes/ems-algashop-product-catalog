package br.dev.allissonnunes.algashop.product.catalog.application.utility;

import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;

public class MediaTypeExtractor {

    public static MediaType extractMediaType(final String fileName) {
        return MediaTypeFactory.getMediaType(fileName).orElseThrow();
    }

}
