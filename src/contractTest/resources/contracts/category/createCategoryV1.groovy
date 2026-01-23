package contracts.category

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method POST()
        headers {
            accept(applicationJson())
            contentType(applicationJson())
        }
        urlPath('/api/v1/categories')
        body([
                name   : value(test("Notebooks"), stub(anyNonBlankString())),
                enabled: value(test(true), stub(anyBoolean()))
        ])
    }
    response {
        status CREATED()
        headers {
            contentType(applicationJson())
            header('Location', value(regex('.+/api/v1/categories/[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}')))
        }
        body([
                id     : anyUuid(),
                name   : fromRequest().body('$.name'),
                enabled: fromRequest().body('$.enabled')
        ])
    }
}

