package ru.shum.shopapp.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.shum.domain.features.registration.users.SaveUserUseCase
import ru.shum.shopapp.ui.catalog.CatalogViewModel
import ru.shum.shopapp.ui.favourite.FavoriteViewModel
import ru.shum.shopapp.ui.productpage.ProductPageScreenViewModel
import ru.shum.shopapp.ui.profile.ProfileViewModel
import ru.shum.shopapp.ui.registration.RegistrationViewModel

val appModule = module {

    viewModel<RegistrationViewModel> {
        RegistrationViewModel(
            validateNameUseCase = get(),
            validatePhoneUseCase = get(),
            saveUserUseCase = get(),
            getUserUseCase = get()
        )
    }

    viewModel<CatalogViewModel> {
        CatalogViewModel(getProductsUseCase = get(), updateProductUseCase = get())
    }

    viewModel<ProductPageScreenViewModel> {
        ProductPageScreenViewModel(updateProductUseCase = get())
    }

    viewModel<ProfileViewModel> {
        ProfileViewModel(getUserUseCase = get(), getFavouritesProductsUseCase = get(), deleteAllUserUseCase = get(), deleteAllProductsUseCase = get())
    }


    viewModel<FavoriteViewModel> {
        FavoriteViewModel(
            updateProductUseCase = get(), getFavouritesProductsUseCase = get()
        )
    }
}