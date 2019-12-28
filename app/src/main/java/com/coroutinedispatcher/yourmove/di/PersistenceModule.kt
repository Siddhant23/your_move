package com.coroutinedispatcher.yourmove.di

import android.app.Application
import com.coroutinedispatcher.yourmove.utils.SHARED_PREFERENCES_TAG
import com.google.firebase.database.FirebaseDatabase
import com.stavro_xhardha.rocket.Rocket
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object PersistenceModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideRocket(application: Application): Rocket =
        Rocket.launch(application, SHARED_PREFERENCES_TAG)

    @Provides
    @JvmStatic
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance()
}
