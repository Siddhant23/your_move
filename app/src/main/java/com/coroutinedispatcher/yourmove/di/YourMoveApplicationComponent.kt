package com.coroutinedispatcher.yourmove.di

import com.coroutinedispatcher.yourmove.YourMoveApplication
import com.squareup.picasso.Picasso
import dagger.BindsInstance
import dagger.Component

@Component(modules = [MediaModule::class])
interface YourMoveApplicationComponent {
    @Component.Factory
    interface Factory{
        fun create(@BindsInstance yourMoveApplication: YourMoveApplication): YourMoveApplicationComponent
    }

    val picasso: Picasso
}