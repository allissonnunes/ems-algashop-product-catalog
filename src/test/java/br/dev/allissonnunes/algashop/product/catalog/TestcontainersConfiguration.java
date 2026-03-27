package br.dev.allissonnunes.algashop.product.catalog;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.mongodb.MongoDBContainer;

@TestConfiguration
public class TestcontainersConfiguration {

    private static final MongoDBContainer MONGODB_CONTAINER = new MongoDBContainer("mongo:8")
            .withCommand("--replSet", "rs0");

    static {
        MONGODB_CONTAINER.start();

        try {
            MONGODB_CONTAINER.execInContainer(
                    "mongosh",
                    "--eval",
                    """
                            rs.initiate({
                                _id: "rs0",
                                members: [
                                    {
                                        _id: 0,
                                        host: "localhost:27017"
                                    }
                                ]
                            })
                            """
            );
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    @ServiceConnection
    MongoDBContainer mongoDBContainer() {
        return MONGODB_CONTAINER;
    }

}
