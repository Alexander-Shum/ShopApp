package ru.shum.data.db.room.product

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.shum.data.api.model.FeedbackDto
import ru.shum.data.api.model.InfoDto
import ru.shum.data.api.model.PriceDto
import ru.shum.data.db.room.product.model.FeedbackEntity
import ru.shum.data.db.room.product.model.InfoEntity
import ru.shum.data.db.room.product.model.PriceEntity

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromPriceEntity(value: PriceEntity): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toPriceEntity(value: String): PriceEntity {
        val type = object : TypeToken<PriceEntity>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromFeedbackEntity(value: FeedbackEntity): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toFeedbackEntity(value: String): FeedbackEntity {
        val type = object : TypeToken<FeedbackEntity>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromInfoEntityList(value: List<InfoEntity>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toInfoEntityList(value: String): List<InfoEntity> {
        val type = object : TypeToken<List<InfoEntity>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }
}
