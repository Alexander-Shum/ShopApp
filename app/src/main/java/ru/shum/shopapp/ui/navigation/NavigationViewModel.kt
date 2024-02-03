package ru.shum.shopapp.ui.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ru.shum.domain.features.catalog.model.Product

class NavigationViewModel : ViewModel() {

    private val _selectedProduct: MutableState<Product?> = mutableStateOf(null)
    var selectedProduct: State<Product?> = _selectedProduct
        private set


    fun setProduct(product: Product) {
        _selectedProduct.value = product
    }
}