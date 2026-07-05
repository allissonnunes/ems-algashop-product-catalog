package br.dev.allissonnunes.algashop.product.catalog.infrastructure.utility.mapper;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "algashop.mapping")
public class ApplicationMappingProperties {

    @NotBlank
    private String imageStorageUrl;

}
