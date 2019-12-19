package com.coroutinedispatcher.yourmove.di

import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

@Module
object MediaModule {
    @Provides
    fun providePicasso(): Picasso = Picasso.get()
}
