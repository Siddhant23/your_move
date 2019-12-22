package com.coroutinedispatcher.yourmove

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.coroutinedispatcher.yourmove.di.DaggerYourMoveApplicationComponent
import com.coroutinedispatcher.yourmove.di.YourMoveApplicationComponent
import timber.log.Timber

class YourMoveApplication : Application() {
    private lateinit var yourMoveApplicationComponent: YourMoveApplicationComponent

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        yourMoveApplicationComponent = DaggerYourMoveApplicationComponent.factory().create(this)
        APPLICATION_COMPONENT = yourMoveApplicationComponent
    }

    companion object {
        private var APPLICATION_COMPONENT: YourMoveApplicationComponent? = null

        @JvmStatic
        fun getYourMoveComponent(): YourMoveApplicationComponent = APPLICATION_COMPONENT!!
    }
}