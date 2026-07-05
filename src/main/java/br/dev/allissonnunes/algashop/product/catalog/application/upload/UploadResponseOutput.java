package br.dev.allissonnunes.algashop.product.catalog.application.upload;

import lombok.Builder;

import java.time.Instant;

@Builder
public record UploadResponseOutput(
        String uploadSignedUrl,
        String remoteFileName,
        Long contentLength,
        String contentType,
        Instant expiresAt
) {

}
