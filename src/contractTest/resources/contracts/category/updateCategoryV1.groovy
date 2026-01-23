package contracts.category

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method PUT()
        headers {
            accept(applicationJson())
            contentType(applicationJson())
        }
        urlPath('/api/v1/categories/019bebc3-a9c3-7fde-9e2c-c65015132616')
        body([
                name   : value(test("GPUs"), stub(anyNonBlankString())),
                enabled: value(test(true), stub(anyBoolean()))
        ])
    }
    response {
        status OK()
        body([
                id     : anyUuid(),
                name   : fromRequest().body('$.name'),
                enabled: fromRequest().body('$.enabled')
        ])
    }
}

