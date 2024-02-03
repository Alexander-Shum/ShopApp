package ru.shum.domain.features.catalog

class DeleteAllProductsUseCase(
    private val repository: ProductsRepository
) {

    suspend fun execute(){
        repository.deleteAllProducts()
    }
}