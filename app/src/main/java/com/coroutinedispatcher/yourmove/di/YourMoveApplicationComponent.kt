package com.coroutinedispatcher.yourmove.di

import android.app.Application
import com.coroutinedispatcher.yourmove.api.YuGiOhApi
import com.coroutinedispatcher.yourmove.model.AppCoroutineDispatchers
import com.coroutinedispatcher.yourmove.ui.quiz.QuizViewModel
import com.squareup.picasso.Picasso
import com.stavro_xhardha.rocket.Rocket
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MediaModule::class, NetworkModule::class, ViewModelModule::class, DispatchersModule::class, PreferencesModules::class])
interface YourMoveApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance yourMoveApplication: Application): YourMoveApplicationComponent
    }

    val picasso: Picasso
    val yuGiOhApi: YuGiOhApi
    val appCoroutineDispatchers: AppCoroutineDispatchers
    val rocket: Rocket
    val quizViewModelFactory: QuizViewModel.Factory
}