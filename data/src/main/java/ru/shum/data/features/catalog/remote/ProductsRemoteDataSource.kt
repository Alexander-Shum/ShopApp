package ru.shum.data.features.catalog.remote

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.shum.data.api.ProductsApi
import ru.shum.data.features.catalog.ProductsRepositoryImpl
import ru.shum.data.mappers.ProductMapper
import ru.shum.domain.features.catalog.model.Product

class ProductsRemoteDataSource(
    private val productsApi: ProductsApi,
    private val mapper: ProductMapper
): ProductsRepositoryImpl.ProductsRemoteDataSourceInter {

    override suspend fun retrieveProductsData(): Result<List<Product>> {
        return Result.runCatching {
            val productsList = withContext(Dispatchers.IO){
                productsApi.getProducts()
            }

            mapper.mapProductList(productsList.items)
        }
    }

}