package br.dev.allissonnunes.algashop.product.catalog.infrastructure.persistence.dataloader;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "algashop.dataloader")
class DataLoaderProperties {

    private boolean enabled;

    private boolean autoDelete;

    private List<DocumentLocation> documentLocations;

    @Data
    public static class DocumentLocation {

        private Resource location;

        private String collectionName;

    }

}
