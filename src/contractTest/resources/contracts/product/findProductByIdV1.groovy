package contracts.product

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method GET()
        headers {
            accept(applicationJson())
        }
        url('/api/v1/products/019bb3a0-5c32-7685-b712-9dd8373525d3')
    }
    response {
        status OK()
        headers {
            contentType(applicationJson())
        }
        body([
                id          : fromRequest().path(3),
                addedAt     : anyIso8601WithOffset(),
                name       : 'Notebook X11',
                brand      : 'Deep Diver',
                regularPrice: 1500.00,
                salePrice   : 1000.00,
                inStock    : true,
                enabled     : true,
                category   : [
                        id  : anyUuid(),
                        name: 'Notebook'
                ],
                description: 'A Gamer Notebook'
        ])
    }
}
