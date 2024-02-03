package ru.shum.shopapp.ui.favourite

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.shum.domain.features.catalog.UpdateProductUseCase
import ru.shum.domain.features.catalog.model.Product
import ru.shum.domain.features.favourite.GetFavouritesProductsUseCase

class FavoriteViewModel(
    private val updateProductUseCase: UpdateProductUseCase,
    private val getFavouritesProductsUseCase: GetFavouritesProductsUseCase
) : ViewModel() {

    var newItem: MutableState<Product?> = mutableStateOf(null)

    private var _products: MutableState<List<Product>> = mutableStateOf(emptyList())
    val products: MutableState<List<Product>> = _products

    private val mainHandler = Handler(Looper.getMainLooper())

    init {
        getFavoritesProducts()
    }

    private fun getFavoritesProducts() {
        viewModelScope.launch {
            val favoritesList = withContext(Dispatchers.IO) {
                getFavouritesProductsUseCase.execute()
            }

            mainHandler.post {
                _products.value = favoritesList
            }
        }
    }

    fun addToFavorite(item: Product) {
        newItem.value = item.copy(favorite = !item.favorite)

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                updateProductUseCase.execute(newItem.value!!)
            }
            getFavoritesProducts()
        }
    }
}