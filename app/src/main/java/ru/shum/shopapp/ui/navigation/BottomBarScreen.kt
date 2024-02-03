package ru.shum.shopapp.ui.navigation

import ru.shum.shopapp.R
import ru.shum.shopapp.ui.util.Constants

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int
) {

    object Main:  BottomBarScreen(
        route = Constants.MAIN,
        title = "Главная",
        icon = R.drawable.main
    )

    object Catalog:  BottomBarScreen(
        route = Constants.CATALOG,
        title = "Каталог",
        icon = R.drawable.catalog
    )

    object Cart:  BottomBarScreen(
        route = Constants.CART,
        title = "Корзина",
        icon = R.drawable.cart
    )

    object Discounts:  BottomBarScreen(
        route = Constants.DISCOUNTS,
        title = "Акции",
        icon = R.drawable.discounts
    )

    object Profile:  BottomBarScreen(
        route = Constants.PROFILE,
        title = "Профиль",
        icon = R.drawable.profile
    )
}