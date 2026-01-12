package contracts.product

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method POST()
        headers {
            accept 'application/json'
            contentType 'application/json'
        }
        urlPath("/api/v1/products") {
            body([
                    name        : "Notebook X11",
                    brand       : "Deep Diver",
                    regularPrice: 1500.00,
                    salePrice   : 1000.00,
                    enabled     : true,
                    categoryId  : "019bb3eb-db94-763b-a698-7cad3005d259",
                    description : "A Gamer Notebook!"
            ])
        }
    }
    response {
        status 201
        headers {
            contentType 'application/json'
        }
        body([
                id          : anyUuid(),
                addedAt     : anyIso8601WithOffset(),
                name        : "Notebook X11",
                brand       : "Deep Diver",
                regularPrice: 1500.00,
                salePrice   : 1000.00,
                inStock     : false,
                enabled     : true,
                category    : [
                        id  : "019bb3eb-db94-763b-a698-7cad3005d259",
                        name: "Notebook"
                ],
                description : "A Gamer Notebook!"
        ])
    }
}

