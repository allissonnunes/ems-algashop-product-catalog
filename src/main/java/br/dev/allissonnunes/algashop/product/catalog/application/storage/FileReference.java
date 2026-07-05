package br.dev.allissonnunes.algashop.product.catalog.application.storage;

import lombok.Builder;
import org.springframework.http.MediaType;

import java.time.Duration;

import static java.util.Objects.requireNonNull;

@Builder
public record FileReference(
        String fileName,
        MediaType contentType,
        Long contentLength,
        Duration expiresIn
) {

    public FileReference {
        requireNonNull(fileName, "File name cannot be null");
        requireNonNull(contentType, "Content type cannot be null");
        requireNonNull(contentLength, "Content length cannot be null");
        requireNonNull(expiresIn, "Expires in cannot be null");

        if (contentLength <= 0) {
            throw new IllegalArgumentException("Content length must be greater than zero");
        }
    }

}
