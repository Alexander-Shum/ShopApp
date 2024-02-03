package ru.shum.data.db.room.product.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val productId: Int = 0,
    val id: String,
    val title: String,
    val subtitle: String,
    val price: PriceEntity,
    val feedback: FeedbackEntity,
    val tags: List<String>,
    val available: Int,
    val description: String,
    val info: List<InfoEntity>,
    val ingredients: String,
    val favorite: Boolean = false
)

@Entity
data class PriceEntity(
    val price: String,
    val discount: Int,
    val priceWithDiscount: String,
    val unit: String
)

@Entity
data class FeedbackEntity(
    val count: Int,
    val rating: Double
)

@Entity
data class InfoEntity(
    val title: String,
    val value: String
)
