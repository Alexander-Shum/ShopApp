package ru.shum.shopapp.di

import org.koin.dsl.module
import ru.shum.domain.features.catalog.DeleteAllProductsUseCase
import ru.shum.domain.features.catalog.GetProductsUseCase
import ru.shum.domain.features.catalog.UpdateProductUseCase
import ru.shum.domain.features.favourite.GetFavouritesProductsUseCase
import ru.shum.domain.features.registration.users.DeleteAllUserUseCase
import ru.shum.domain.features.registration.users.GetUserUseCase
import ru.shum.domain.features.registration.users.SaveUserUseCase
import ru.shum.domain.features.registration.validation.ValidateNameUseCase
import ru.shum.domain.features.registration.validation.ValidatePhoneUseCase

val domainModule = module {
    single<GetProductsUseCase> {
        GetProductsUseCase(repository = get())
    }

    single<UpdateProductUseCase> {
        UpdateProductUseCase(repository = get())
    }

    single<DeleteAllUserUseCase> {
        DeleteAllUserUseCase(repository = get())
    }

    single<DeleteAllProductsUseCase> {
        DeleteAllProductsUseCase(repository = get())
    }

    single<GetFavouritesProductsUseCase> {
        GetFavouritesProductsUseCase(repository = get())
    }

    single<ValidateNameUseCase> {
        ValidateNameUseCase()
    }

    single<ValidatePhoneUseCase> {
        ValidatePhoneUseCase()
    }

    single<SaveUserUseCase> {
        SaveUserUseCase(userRepository = get())
    }

    single<GetUserUseCase> {
        GetUserUseCase(userRepository = get())
    }
}