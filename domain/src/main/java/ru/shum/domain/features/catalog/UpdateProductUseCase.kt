package ru.shum.domain.features.catalog

import ru.shum.domain.features.catalog.model.Product

class UpdateProductUseCase(
    private val repository: ProductsRepository
) {

    suspend fun execute(item: Product){
        return repository.updateProduct(item)
    }

}