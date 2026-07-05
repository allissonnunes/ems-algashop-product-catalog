package br.dev.allissonnunes.algashop.product.catalog.application.upload;

import br.dev.allissonnunes.algashop.product.catalog.application.storage.FileReference;
import br.dev.allissonnunes.algashop.product.catalog.application.storage.StorageProvider;
import br.dev.allissonnunes.algashop.product.catalog.application.utility.MediaTypeExtractor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadRequestApplicationService {

    private static final List<MediaType> ALLOWED_MEDIA_TYPES = List.of(MediaType.IMAGE_JPEG, MediaType.IMAGE_PNG);

    private final StorageProvider storageProvider;

    public UploadResponseOutput requestPreSignedUrl(final UploadRequestInput input) {
        final MediaType mediaType = MediaTypeExtractor.extractMediaType(input.originalFileName());
        if (ALLOWED_MEDIA_TYPES.stream().noneMatch(mediaType::isCompatibleWith)) {
            throw new IllegalArgumentException("Invalid file type");
        }
        final String extension = FilenameUtils.getExtension(input.originalFileName());
        final FileReference fileReference = FileReference.builder()
                .fileName(UUID.randomUUID() + "." + extension)
                .contentType(mediaType)
                .contentLength(input.contentLength())
                .expiresIn(Duration.ofMinutes(5L))
                .build();
        final URL preSignedUrl = this.storageProvider.requestUploadUrl(fileReference);
        final Instant expiryInstant = Instant.now().plus(fileReference.expiresIn());
        return UploadResponseOutput.builder()
                .uploadSignedUrl(preSignedUrl.toString())
                .remoteFileName(fileReference.fileName())
                .contentLength(fileReference.contentLength())
                .contentType(fileReference.contentType().toString())
                .expiresAt(expiryInstant)
                .build();
    }

}
