package com.coroutinedispatcher.yourmove.di

import android.app.Application
import androidx.room.Room
import com.coroutinedispatcher.yourmove.db.YuGiOhDao
import com.coroutinedispatcher.yourmove.db.YuGiOhDatabase
import com.coroutinedispatcher.yourmove.utils.SHARED_PREFERENCES_TAG
import com.stavro_xhardha.rocket.Rocket
import dagger.Lazy
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

//to write article about dagger encapsulation

@Retention(AnnotationRetention.BINARY)
@Qualifier
private annotation class InternalApi

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
    @InternalApi
    fun provideDb(application: Application): YuGiOhDatabase =
        Room.databaseBuilder(application, YuGiOhDatabase::class.java, "YuGiOhDatabase.db")
            .createFromAsset("database/yugioh_database.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @JvmStatic
    @Singleton
    fun provieYuGiOhDao(@InternalApi yuGiOhDatabase: Lazy<YuGiOhDatabase>): YuGiOhDao =
        yuGiOhDatabase.get().yuGiOhDao()
}
