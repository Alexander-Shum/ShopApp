package ru.shum.shopapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.shum.shopapp.di.appModule
import ru.shum.shopapp.di.dataModule
import ru.shum.shopapp.di.domainModule

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@App)
            modules(dataModule, appModule, domainModule)
        }
    }

}