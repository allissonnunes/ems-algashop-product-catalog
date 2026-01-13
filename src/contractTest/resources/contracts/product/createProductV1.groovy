package contracts.product

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method POST()
        headers {
            accept(applicationJson())
            contentType(applicationJson())
        }
        urlPath('/api/v1/products') {
            body([
                    name        : value(test('Notebook X11'), stub(anyNonBlankString())),
                    brand       : value(test('Deep Diver'), stub(anyNonBlankString())),
                    regularPrice: value(test(1500.00), stub(anyNumber())),
                    salePrice   : value(test(1000.00), stub(anyNumber())),
                    enabled     : value(test(true), stub(anyBoolean())),
                    categoryId  : value(test('019bb3eb-db94-763b-a698-7cad3005d259'), stub(anyUuid())),
                    description : value(test('A Gamer Notebook!'), stub(optional(anyNonBlankString())))
            ])
        }
    }
    response {
        status CREATED()
        headers {
            contentType(applicationJson())
        }
        body([
                id          : anyUuid(),
                addedAt     : anyIso8601WithOffset(),
                name        : fromRequest().body('$.name'),
                brand       : fromRequest().body('$.brand'),
                regularPrice: fromRequest().body('$.regularPrice'),
                salePrice   : fromRequest().body('$.salePrice'),
                inStock     : false,
                enabled     : fromRequest().body('$.enabled'),
                category    : [
                        id  : fromRequest().body('$.categoryId'),
                        name: 'Notebook'
                ],
                description : fromRequest().body('$.description')
        ])
    }
}

