package contracts.category

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method GET()
        headers {
            accept(applicationJson())
        }
        urlPath('/api/v1/categories') {
            queryParameters {
                parameter('page', value(stub(optional(anyNumber())), test(0)))
                parameter('size', value(stub(optional(anyNumber())), test(10)))
            }
        }
    }
    response {
        status OK()
        body([
                content      : [
                        [
                                id     : anyUuid(),
                                name   : anyNonBlankString(),
                                enabled: anyBoolean()
                        ],
                        [
                                id     : anyUuid(),
                                name   : anyNonBlankString(),
                                enabled: anyBoolean()
                        ]
                ],
                number       : fromRequest().query('page'),
                size         : fromRequest().query('size'),
                totalElements: 2,
                totalPages   : 1,
        ])
    }
}

