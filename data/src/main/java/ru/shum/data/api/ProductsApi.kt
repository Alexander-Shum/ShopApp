package ru.shum.data.api

import retrofit2.http.GET
import ru.shum.data.api.model.ProductDto
import ru.shum.data.api.model.ProductListDto

interface ProductsApi {

    @GET("v3/97e721a7-0a66-4cae-b445-83cc0bcf9010")
    suspend fun getProducts(): ProductListDto

    companion object {
        const val BASE_URL = "https://run.mocky.io/"
    }
}