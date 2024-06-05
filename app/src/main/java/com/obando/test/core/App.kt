package com.obando.test.core

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import com.obando.test.core.module.baseViewModelModule
import com.obando.test.core.module.homeRepositoryModule
import com.obando.test.core.module.homeViewModelModule
import com.obando.test.core.module.networkUtilModule
import com.obando.test.core.network.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    //Modules
                    baseViewModelModule,
                    networkUtilModule,
                    networkModule,
                    homeRepositoryModule,
                    homeViewModelModule
                )
            )
        }
    }
}