package ru.shum.data.api.model

data class ProductListDto(
    val items: List<ProductDto>
)

data class ProductDto(
    val id: String,
    val title: String,
    val subtitle: String,
    val price: PriceDto,
    val feedback: FeedbackDto,
    val tags: List<String>,
    val available: Int,
    val description: String,
    val info: List<InfoDto>,
    val ingredients: String
)

data class PriceDto(
    val price: String,
    val discount: Int,
    val priceWithDiscount: String,
    val unit: String
)

data class FeedbackDto(
    val count: Int,
    val rating: Double
)

data class InfoDto(
    val title: String,
    val value: String
)
