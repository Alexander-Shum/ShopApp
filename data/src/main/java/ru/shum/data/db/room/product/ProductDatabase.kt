package ru.shum.data.db.room.product

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.shum.data.api.model.ProductDto
import ru.shum.data.db.room.product.model.ProductEntity

@TypeConverters(Converters::class)
@Database(
    entities = [
        ProductEntity::class
    ],
    version = 2
)
abstract class ProductDatabase: RoomDatabase() {

    abstract fun productDao(): ProductDao
}