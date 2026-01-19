package contracts.product

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method PUT()
        headers {
            accept(applicationJson())
            contentType(applicationJson())
        }
        urlPath('/api/v1/products/019bb3a0-5c32-7685-b712-9dd8373525d3')
        body([
                name        : value(
                        test("Notebook X11"),
                        stub(nonBlank())
                ),
                brand       : value(
                        test("Deep Diver"),
                        stub(nonBlank())
                ),
                regularPrice: value(
                        test(1500.00),
                        stub(number())
                ),
                salePrice   : value(
                        test(1000.00),
                        stub(number())
                ),
                enabled     : value(
                        test(true),
                        stub(anyBoolean())
                ),
                categoryId  : value(
                        test("f5ab7a1e-37da-41e1-892b-a1d38275c2f2"),
                        stub(anyUuid())
                ),
                description : value(
                        test("A Gamer Notebook"),
                        stub(optional(nonBlank()))
                )
        ])
    }
    response {
        status OK()
        headers {
            contentType(applicationJson())
        }
        body([
                id          : fromRequest().path(3),
                addedAt     : anyIso8601WithOffset(),
                name        : fromRequest().body('$.name'),
                brand       : fromRequest().body('brand'),
                regularPrice: fromRequest().body('$.regularPrice'),
                salePrice   : fromRequest().body('$.salePrice'),
                inStock     : anyBoolean(),
                enabled     : fromRequest().body('$.enabled'),
                category    : [
                        id  : anyUuid(),
                        name: "Notebook"
                ],
                description : fromRequest().body('$.description'),
        ])
    }
}

