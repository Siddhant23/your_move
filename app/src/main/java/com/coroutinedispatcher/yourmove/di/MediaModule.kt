package com.coroutinedispatcher.yourmove.di

import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object MediaModule {
    @Provides
    @JvmStatic
    @Singleton
    fun providePicasso(): Picasso = Picasso.get()
}
