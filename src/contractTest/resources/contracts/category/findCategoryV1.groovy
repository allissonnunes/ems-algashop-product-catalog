package contracts.category

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method GET()
        headers {
            accept(applicationJson())
        }
        urlPath('/api/v1/categories/019bdbc2-037f-7983-9af2-76cbd2ab94c5')
    }
    response {
        status OK()
        body([
                id     : fromRequest().path(3),
                name   : anyNonBlankString(),
                enabled: anyBoolean()
        ])
    }
}

