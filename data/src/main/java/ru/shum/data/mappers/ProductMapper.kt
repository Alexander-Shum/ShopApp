package ru.shum.data.mappers

import ru.shum.data.api.model.FeedbackDto
import ru.shum.data.api.model.InfoDto
import ru.shum.data.api.model.PriceDto
import ru.shum.data.api.model.ProductDto
import ru.shum.data.db.room.product.model.FeedbackEntity
import ru.shum.data.db.room.product.model.InfoEntity
import ru.shum.data.db.room.product.model.PriceEntity
import ru.shum.data.db.room.product.model.ProductEntity
import ru.shum.domain.features.catalog.model.Feedback
import ru.shum.domain.features.catalog.model.Info
import ru.shum.domain.features.catalog.model.Price
import ru.shum.domain.features.catalog.model.Product

class ProductMapper {

    fun mapProductList(products: List<ProductDto>): List<Product> {
        return products.map { mapProduct(it) }
    }

    fun mapProductEntityList(products: List<ProductEntity>): List<Product> {
        return products.map { mapProductFromEntity(it) }
    }

    private fun mapProduct(productDto: ProductDto): Product {
        return Product(
            id = productDto.id,
            title = productDto.title,
            subtitle = productDto.subtitle,
            price = mapPrice(productDto.price),
            feedback = mapFeedback(productDto.feedback),
            tags = productDto.tags,
            available = productDto.available,
            description = productDto.description,
            info = mapInfoList(productDto.info),
            ingredients = productDto.ingredients
        )
    }

    fun mapProductFromEntity(product: ProductEntity): Product {
        return Product(
            productId = product.productId,
            id = product.id,
            title = product.title,
            subtitle = product.subtitle,
            price = mapPriceFromEntity(product.price),
            feedback = mapFeedbackFromEntity(product.feedback),
            tags = product.tags,
            available = product.available,
            description = product.description,
            info = mapInfoListFromEntit(product.info),
            ingredients = product.ingredients,
            favorite = product.favorite
        )
    }

    private fun mapPriceFromEntity(price: PriceEntity): Price {
        return Price(
            price = price.price,
            discount = price.discount,
            priceWithDiscount = price.priceWithDiscount,
            unit = price.unit
        )
    }

    private fun mapFeedbackFromEntity(feedback: FeedbackEntity): Feedback {
        return Feedback(
            count = feedback.count,
            rating = feedback.rating
        )
    }

    private fun mapInfoListFromEntit(infoEntityList: List<InfoEntity>): List<Info> {
        return infoEntityList.map { mapFromInfo(it) }
    }

    private fun mapFromInfo(info: InfoEntity): Info {
        return Info(
            title = info.title,
            value = info.value
        )
    }

    /////
    fun mapProductEntity(product: Product): ProductEntity {
        return ProductEntity(
            productId = product.productId,
            id = product.id,
            title = product.title,
            subtitle = product.subtitle,
            price = mapPriceEntity(product.price),
            feedback = mapFeedbackEntity(product.feedback),
            tags = product.tags,
            available = product.available,
            description = product.description,
            info = mapInfoListEntit(product.info),
            ingredients = product.ingredients,
            favorite = product.favorite
        )
    }

    private fun mapPriceEntity(price: Price): PriceEntity {
        return PriceEntity(
            price = price.price,
            discount = price.discount,
            priceWithDiscount = price.priceWithDiscount,
            unit = price.unit
        )
    }

    private fun mapFeedbackEntity(feedback: Feedback): FeedbackEntity {
        return FeedbackEntity(
            count = feedback.count,
            rating = feedback.rating
        )
    }

    private fun mapInfoListEntit(infoEntityList: List<Info>): List<InfoEntity> {
        return infoEntityList.map { mapInfo(it) }
    }

    private fun mapInfo(info: Info): InfoEntity {
        return InfoEntity(
            title = info.title,
            value = info.value
        )
    }

    //////
    private fun mapPrice(priceDto: PriceDto): Price {
        return Price(
            price = priceDto.price,
            discount = priceDto.discount,
            priceWithDiscount = priceDto.priceWithDiscount,
            unit = priceDto.unit
        )
    }

    private fun mapFeedback(feedbackDto: FeedbackDto): Feedback {
        return Feedback(
            count = feedbackDto.count,
            rating = feedbackDto.rating
        )
    }

    private fun mapInfoList(infoDtoList: List<InfoDto>): List<Info> {
        return infoDtoList.map { mapInfo(it) }
    }

    private fun mapInfo(infoDto: InfoDto): Info {
        return Info(
            title = infoDto.title,
            value = infoDto.value
        )
    }
}