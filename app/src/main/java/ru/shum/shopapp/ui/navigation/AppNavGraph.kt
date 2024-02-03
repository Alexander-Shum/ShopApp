package ru.shum.shopapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.shum.shopapp.ui.cart.CartScreen
import ru.shum.shopapp.ui.catalog.CatalogScreen
import ru.shum.shopapp.ui.discounts.DiscountsScreen
import ru.shum.shopapp.ui.favourite.FavoriteScreen
import ru.shum.shopapp.ui.main.MainScreen
import ru.shum.shopapp.ui.productpage.ProductPageScreen
import ru.shum.shopapp.ui.profile.ProfileScreen
import ru.shum.shopapp.ui.registration.RegistrationScreen

@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    val navigationViewModel: NavigationViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Registration.route
    ) {

        composable(Screen.Registration.route) {
            RegistrationScreen(navController)
        }

        composable(Screen.Favourite.route) {
            FavoriteScreen(navController)
        }

        composable(BottomBarScreen.Main.route) {
            MainScreen(navController)
        }

        composable(BottomBarScreen.Catalog.route) {
            CatalogScreen(navController, navigationViewModel)
        }

        composable(BottomBarScreen.Cart.route) {
            CartScreen(navController)
        }

        composable(BottomBarScreen.Discounts.route) {
            DiscountsScreen(navController)
        }

        composable(BottomBarScreen.Profile.route) {
            ProfileScreen(navController)
        }

        composable(Screen.ProductPage.route) {
            ProductPageScreen(navController, navigationViewModel)
        }
    }
}