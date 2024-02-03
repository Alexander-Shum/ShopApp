package ru.shum.data.features.catalog

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.shum.domain.features.catalog.ProductsRepository
import ru.shum.domain.features.catalog.model.Product

class ProductsRepositoryImpl(
    private val remote: ProductsRemoteDataSourceInter,
    private val local: ProductsLocalDataSourceInter
) : ProductsRepository {

    override suspend fun getProducts(): List<Product> {
        if (local.getAllProducts().isEmpty()) {
            remote.retrieveProductsData().onSuccess {
                local.insertProducts(it)
            }
        }
        return local.getAllProducts()
    }

    override suspend fun getFavoritesProducts(): List<Product> {
        return local.getFavouritesProducts()
    }

    override suspend fun deleteAllProducts() {
        local.deleteAll()
    }

    override suspend fun updateProduct(product: Product) {
        local.updateProduct(product)
    }

    interface ProductsRemoteDataSourceInter {
        suspend fun retrieveProductsData(
        ): Result<List<Product>>
    }

    interface ProductsLocalDataSourceInter {
        suspend fun insertProducts(products: List<Product>)
        suspend fun getAllProducts(): List<Product>
        suspend fun getFavouritesProducts(): List<Product>
        suspend fun updateProduct(product: Product)
        suspend fun deleteAll()
    }
}