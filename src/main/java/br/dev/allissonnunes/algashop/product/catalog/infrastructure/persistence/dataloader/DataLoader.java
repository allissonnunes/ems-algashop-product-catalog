package br.dev.allissonnunes.algashop.product.catalog.infrastructure.persistence.dataloader;

import com.mongodb.client.MongoCollection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonArray;
import org.bson.BsonValue;
import org.bson.Document;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements ApplicationRunner {

    private final DataLoaderProperties properties;

    private final MongoOperations mongoOperations;

    @Override
    public void run(final @NonNull ApplicationArguments args) throws Exception {
        if (!properties.isEnabled()) {
            log.info("Loading data into database disabled");
            return;
        }
        log.info("Loading data into database");

        final List<DataLoaderProperties.DocumentLocation> documentLocations = properties.getDocumentLocations();
        if (documentLocations.isEmpty()) {
            log.info("No data to load");
            return;
        }

        log.info("Loading data from {}", documentLocations);

        documentLocations.forEach(this::insertDocuments);

        log.info("Data loaded successfully");
    }

    private void insertDocuments(final DataLoaderProperties.DocumentLocation documentLocation) {
        if (documentLocation == null) {
            return;
        }

        final List<Document> documents = convertJsonToDocuments(documentLocation);
        if (documents.isEmpty()) {
            log.warn("Document {} is empty", documentLocation.getLocation());
            return;
        }

        try {
            final MongoCollection<Document> targetCollection = mongoOperations.getCollection(documentLocation.getCollectionName());
            if (properties.isAutoDelete()) {
                log.info("Deleting all documents from collection {}", documentLocation.getCollectionName());
                targetCollection.deleteMany(new Document());
            }

            log.info("Inserting {} documents into collection {}", documents.size(), documentLocation.getCollectionName());
            targetCollection.insertMany(documents);
        } catch (final Exception e) {
            log.error("Error when inserting documents into collection {}", documentLocation.getCollectionName(), e);
        }
    }

    private List<Document> convertJsonToDocuments(final DataLoaderProperties.DocumentLocation sourceLocation) {
        if (sourceLocation == null) {
            return Collections.emptyList();
        }

        try {
            final String rawJsonData = sourceLocation.getLocation().getContentAsString(StandardCharsets.UTF_8);
            final BsonArray parsedBsonArray = BsonArray.parse(rawJsonData);

            return parsedBsonArray.stream()
                    .map(BsonValue::toString)
                    .map(Document::parse)
                    .toList();
        } catch (final IOException e) {
            log.error("Error when parsing JSON data", e);
            return Collections.emptyList();
        }
    }

}
