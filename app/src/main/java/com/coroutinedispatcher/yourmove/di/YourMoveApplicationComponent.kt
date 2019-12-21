package com.coroutinedispatcher.yourmove.di

import com.coroutinedispatcher.yourmove.YourMoveApplication
import com.coroutinedispatcher.yourmove.api.YuGiOhApi
import com.coroutinedispatcher.yourmove.model.AppCoroutineDispatchers
import com.coroutinedispatcher.yourmove.ui.quiz.QuizViewModel
import com.squareup.picasso.Picasso
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MediaModule::class, NetworkModule::class, ViewModelModule::class, DispatchersModule::class])
interface YourMoveApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance yourMoveApplication: YourMoveApplication): YourMoveApplicationComponent
    }

    val picasso: Picasso
    val yuGiOhApi: YuGiOhApi
    val appCoroutineDispatchers: AppCoroutineDispatchers
    val quizViewModelFactory: QuizViewModel.Factory
}