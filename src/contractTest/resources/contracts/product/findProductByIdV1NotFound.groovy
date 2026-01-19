package contracts.product

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method GET()
        headers {
            accept(applicationJson())
        }
        url('/api/v1/products/019bd762-8fed-7119-89b3-ae8c733727e8')
    }
    response {
        status NOT_FOUND()
        headers {
            contentType('application/problem+json')
        }
        body([
                status  : NOT_FOUND(),
                type    : '/errors/not-found',
                title   : 'Not Found',
                detail  : 'Product 019bd762-8fed-7119-89b3-ae8c733727e8 not found',
                instance: fromRequest().path()
        ])
    }
}
