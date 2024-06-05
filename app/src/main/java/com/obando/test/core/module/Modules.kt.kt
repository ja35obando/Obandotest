package com.obando.test.core.module

import com.obando.test.core.base.BaseViewModel
import com.obando.test.core.repository.HomeRepository
import com.obando.test.ui.home.viewmodel.HomeViewModel
import com.obando.test.utlis.NetworkUtil
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.factory
import org.koin.dsl.module

val baseViewModelModule = module {
    factory { BaseViewModel() }
}

val networkUtilModule = module {
    single { NetworkUtil(androidContext()) }
}

val homeRepositoryModule = module {
    factory {HomeRepository(get())}
}

val homeViewModelModule = module {
    factory{HomeViewModel(get())}
}