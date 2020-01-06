package com.coroutinedispatcher.yourmove.di

import android.app.Application
import com.coroutinedispatcher.yourmove.db.YuGiOhDao
import com.coroutinedispatcher.yourmove.model.AppCoroutineDispatchers
import com.coroutinedispatcher.yourmove.ui.advanced_search.AdvancedSearchViewModel
import com.coroutinedispatcher.yourmove.ui.search.HomeViewModel
import com.squareup.picasso.Picasso
import com.stavro_xhardha.rocket.Rocket
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MediaModule::class, ViewModelModule::class, PersistenceModule::class, DispatchersModule::class])
interface YourMoveApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance yourMoveApplication: Application): YourMoveApplicationComponent
    }

    val picasso: Picasso
    val rocket: Rocket
    val homeViewModelFactory: HomeViewModel.Factory
    val appCoroutineDispatchers: AppCoroutineDispatchers
    val yuGiOhDao: YuGiOhDao
    val advancedSearchViewModel: AdvancedSearchViewModel
}