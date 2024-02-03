package ru.shum.data.db.room.product

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.shum.data.api.model.ProductDto
import ru.shum.data.db.room.product.model.ProductEntity

@Dao
abstract class ProductDao {

    @Insert
    abstract fun insertProduct(product: ProductEntity)

    @Query("SELECT * FROM products")
    abstract fun getAllProducts(): List<ProductEntity>

    @Query("SELECT * FROM products WHERE favorite = 1")
    abstract fun getFavouritesProducts(): List<ProductEntity>

    @Update
    abstract fun updateProduct(product: ProductEntity)

    @Query("DELETE FROM products")
    abstract fun deleteAll()
}