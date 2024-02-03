package ru.shum.domain.features.catalog

import ru.shum.domain.features.catalog.model.Product

class GetProductsUseCase(
    private val repository: ProductsRepository
) {

    suspend fun execute(): List<Product>{
        return repository.getProducts()
    }

}