package br.dev.allissonnunes.algashop.product.catalog.infrastructure.storage.fake;

import br.dev.allissonnunes.algashop.product.catalog.application.storage.FileReference;
import br.dev.allissonnunes.algashop.product.catalog.application.storage.StorageProvider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class StorageProviderFakeImpl implements StorageProvider {

    @Override
    @SneakyThrows(MalformedURLException.class)
    public URL requestUploadUrl(final FileReference fileReference) {
        return URI.create(
                "http://localhost:4566/%s?token=%s".formatted(fileReference.fileName(), UUID.randomUUID().toString())
        ).toURL();
    }

    @Override
    public void deleteFile(final String remoteFileName) {

    }

    @Override
    public boolean fileExists(final String remoteFileName) {
        return !remoteFileName.equalsIgnoreCase("fail.png");
    }

}
