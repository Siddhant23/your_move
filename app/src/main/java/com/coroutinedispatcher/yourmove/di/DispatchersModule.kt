package com.coroutinedispatcher.yourmove.di

import com.coroutinedispatcher.yourmove.model.AppCoroutineDispatchers
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
object DispatchersModule {
    @Provides
    @JvmStatic
    @Singleton
    fun provideAppCoroutineDispatchers(): AppCoroutineDispatchers = AppCoroutineDispatchers(
        mainDispatcher = Dispatchers.Main,
        ioDispatcher = Dispatchers.IO
    )
}