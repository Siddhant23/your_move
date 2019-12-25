package com.coroutinedispatcher.yourmove.di

import com.coroutinedispatcher.yourmove.api.YuGiOhApi
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Lazy
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
object NetworkModule {
    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    private annotation class InternalApi

    @JvmStatic
    @Provides
    @Singleton
    fun provideYuGiOhApi(@InternalApi retrofit: Lazy<Retrofit>): YuGiOhApi =
        retrofit.get().create(YuGiOhApi::class.java)

    @JvmStatic
    @Provides
    @Singleton
    @InternalApi
    fun provideRetrofit(@InternalApi client: Lazy<OkHttpClient>): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://db.ygoprodeck.com/api/v5/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client.get())
            .build()

    @JvmStatic
    @Provides
    @Singleton
    @InternalApi
    fun provideOkHtppClient(@InternalApi interceptor: Lazy<HttpLoggingInterceptor>): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor.get())
            .build()

    @JvmStatic
    @Provides
    @Singleton
    @InternalApi
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @JvmStatic
    @Singleton
    fun provideFirebaseDatabaseReference(): DatabaseReference =
        FirebaseDatabase.getInstance().reference
}