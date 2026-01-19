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
            body('''\
                {
                    "name": "",
                    "brand": "",
                    "regularPrice": "",
                    "salePrice": "",
                    "enabled": "",
                    "categoryId": "",
                    "description": ""
                }
                ''')
        }
    }
    response {
        status BAD_REQUEST()
        headers {
            contentType('application/problem+json')
        }
        body([
                status  : BAD_REQUEST(),
                type    : '/errors/invalid-fields',
                title   : 'Invalid fields',
                detail  : 'One or more fields are invalid.',
                instance: fromRequest().path(),
                fields  : [
                        name        : anyNonBlankString(),
                        brand       : anyNonBlankString(),
                        regularPrice: anyNonBlankString(),
                        salePrice   : anyNonBlankString(),
                        enabled     : anyNonBlankString(),
                        categoryId  : anyNonBlankString()
                ]
        ])
    }
}