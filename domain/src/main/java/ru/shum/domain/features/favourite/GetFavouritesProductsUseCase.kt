package ru.shum.domain.features.favourite

import ru.shum.domain.features.catalog.ProductsRepository
import ru.shum.domain.features.catalog.model.Product

class GetFavouritesProductsUseCase(
    private val repository: ProductsRepository
) {

    suspend fun execute(): List<Product>{
        return repository.getFavoritesProducts()
    }
}