package ru.shum.domain.features.catalog

import ru.shum.domain.features.catalog.model.Product

interface ProductsRepository {

    suspend fun getProducts(): List<Product>
    suspend fun getFavoritesProducts(): List<Product>
    suspend fun deleteAllProducts()
    suspend fun updateProduct(product: Product)
}