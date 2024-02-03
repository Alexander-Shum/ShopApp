package ru.shum.shopapp.ui.catalog

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.shum.data.db.room.product.model.ProductEntity
import ru.shum.domain.features.catalog.GetProductsUseCase
import ru.shum.domain.features.catalog.UpdateProductUseCase
import ru.shum.domain.features.catalog.model.Product
import ru.shum.shopapp.R

class CatalogViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val updateProductUseCase: UpdateProductUseCase
) : ViewModel() {

    var products: MutableState<List<Product>> = mutableStateOf(emptyList())
    val productsBase: MutableState<List<Product>> = mutableStateOf(emptyList())

    var selectedTag: MutableState<Int> = mutableIntStateOf(0)

    val tagsList = listOf(
        "Каталог",
        "Лицо",
        "Тело",
        "Загар",
        "Маски"
    )

    fun getProducts() {


        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                products.value = getProductsUseCase.execute()
                productsBase.value = products.value
                changeSortState(0)
            }
        }

    }

    fun addToFavorite(item: Product) {
        val newItem = item.copy(favorite = !item.favorite)

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                updateProductUseCase.execute(newItem)
                getProducts()
            }
        }
    }

    fun selectTag(tag: Int) {
        selectedTag.value = tag
        products.value = productsBase.value
        when (tag) {
            1 -> {
                products.value = products.value.filter { it.tags.contains("face") }
            }

            2 -> {
                products.value = products.value.filter { it.tags.contains("body") }
            }

            3 -> {
                products.value = products.value.filter { it.tags.contains("suntan") }
            }

            4 -> {
                products.value = products.value.filter { it.tags.contains("mask") }
            }
        }
    }

    fun changeSortState(sort: Int) {
        when (sort) {
            0 -> {
                products.value = products.value.sortedByDescending { it.feedback.rating }
            }

            1 -> {
                products.value = products.value.sortedBy { it.price.priceWithDiscount.toInt() }
            }

            2 -> {
                products.value =
                    products.value.sortedByDescending { it.price.priceWithDiscount.toInt() }
            }
        }
    }
}