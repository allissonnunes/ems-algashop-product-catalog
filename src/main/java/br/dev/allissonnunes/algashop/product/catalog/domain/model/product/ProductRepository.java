package br.dev.allissonnunes.algashop.product.catalog.domain.model.product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends MongoRepository<Product, UUID> {

    List<Product> findAllByEnabled(Boolean enabled);

}
