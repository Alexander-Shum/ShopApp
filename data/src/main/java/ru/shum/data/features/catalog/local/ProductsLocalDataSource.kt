package ru.shum.data.features.catalog.local

import ru.shum.data.db.room.product.ProductDao
import ru.shum.data.features.catalog.ProductsRepositoryImpl
import ru.shum.data.mappers.ProductMapper
import ru.shum.domain.features.catalog.model.Product

class ProductsLocalDataSource(
    private val productDao: ProductDao,
    private val mapper: ProductMapper
) : ProductsRepositoryImpl.ProductsLocalDataSourceInter {
    override suspend fun insertProducts(products: List<Product>) {
        products.forEach {
            productDao.insertProduct(mapper.mapProductEntity(it))
        }
    }

    override suspend fun getAllProducts(): List<Product> {

        return mapper.mapProductEntityList(productDao.getAllProducts())
    }

    override suspend fun getFavouritesProducts(): List<Product> {
        return mapper.mapProductEntityList(productDao.getFavouritesProducts())
    }

    override suspend fun updateProduct(product: Product) {
        productDao.updateProduct(mapper.mapProductEntity(product))
    }

    override suspend fun deleteAll() {
        productDao.deleteAll()
    }
}