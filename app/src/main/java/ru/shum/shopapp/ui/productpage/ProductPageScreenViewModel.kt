package ru.shum.shopapp.ui.productpage

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.shum.domain.features.catalog.UpdateProductUseCase
import ru.shum.domain.features.catalog.model.Product

class ProductPageScreenViewModel(
    private val updateProductUseCase: UpdateProductUseCase
): ViewModel() {

    var newItem: MutableState<Product?> = mutableStateOf(null)

    fun setProduct(product: Product){
        newItem.value = product
    }

    fun addToFavorite(item: Product) {
        newItem.value = item.copy(favorite = !item.favorite)

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                updateProductUseCase.execute(newItem.value!!)
            }
        }
    }
}