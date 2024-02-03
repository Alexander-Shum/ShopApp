package ru.shum.shopapp.ui.navigation

import ru.shum.shopapp.ui.util.Constants

sealed class Screen(val route: String) {
    object Registration : Screen(Constants.REGISTRATION)
    object ProductPage : Screen(Constants.PRODUCT_PAGE)
    object Favourite : Screen(Constants.FAVOURITE)
}