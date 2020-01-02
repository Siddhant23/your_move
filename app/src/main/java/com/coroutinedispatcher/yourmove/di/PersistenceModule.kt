package com.coroutinedispatcher.yourmove.di

import android.app.Application
import androidx.room.Room
import com.coroutinedispatcher.yourmove.db.YuGiOhDatabase
import com.coroutinedispatcher.yourmove.utils.SHARED_PREFERENCES_TAG
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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
    fun provideMoshiReader(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @JvmStatic
    @Singleton
    fun provideDb(application: Application): YuGiOhDatabase =
        Room.databaseBuilder(application, YuGiOhDatabase::class.java, "YuGiOhDatabase.db")
            .createFromAsset("database/yugioh_database.db")
            .build()
}
