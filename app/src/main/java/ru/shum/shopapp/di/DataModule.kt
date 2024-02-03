package ru.shum.shopapp.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.shum.data.api.ProductsApi
import ru.shum.data.db.room.product.ProductDatabase
import ru.shum.data.db.room.user.UsersDatabase
import ru.shum.data.features.catalog.ProductsRepositoryImpl
import ru.shum.data.features.catalog.local.ProductsLocalDataSource
import ru.shum.data.features.catalog.remote.ProductsRemoteDataSource
import ru.shum.data.features.registration.UserRepositoryImpl
import ru.shum.data.mappers.ProductMapper
import ru.shum.data.mappers.UserMapper
import ru.shum.domain.features.catalog.ProductsRepository
import ru.shum.domain.features.registration.users.UserRepository

val dataModule = module {

    single<ProductsRepository> {
        ProductsRepositoryImpl(remote = get(), local = get())
    }

    single<ProductsRepositoryImpl.ProductsRemoteDataSourceInter> {
        ProductsRemoteDataSource(productsApi = get(), mapper = get())
    }

    single<ProductsRepositoryImpl.ProductsLocalDataSourceInter> {
        ProductsLocalDataSource(productDao = get(), mapper = get())
    }

    single<ProductsApi>(named("productsApi")) {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ProductsApi.BASE_URL)
            .build()
            .create(ProductsApi::class.java)
    }

    single<ProductsApi> {
        get<ProductsApi>(named("productsApi"))
    }

    single<UserRepository> {
        UserRepositoryImpl(usersDao = get(), mapper = get())
    }

    single<UserMapper> {
        UserMapper()
    }

    single<ProductMapper> {
        ProductMapper()
    }

    single {
        Room.databaseBuilder(androidContext(), UsersDatabase::class.java, "users_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        Room.databaseBuilder(androidContext(), ProductDatabase::class.java, "products_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<UsersDatabase>().usersDao() }
    single { get<ProductDatabase>().productDao() }
}