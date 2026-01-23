package contracts.category

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method DELETE()
        headers {
            accept(applicationJson())
        }
        urlPath('/api/v1/categories/019bebdc-c17e-7099-8333-2d31ad09dcdd')
    }
    response {
        status NO_CONTENT()
    }
}

