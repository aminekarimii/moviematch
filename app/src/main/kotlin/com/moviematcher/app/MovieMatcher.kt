package com.moviematcher.app

import android.app.Application
import com.moviematcher.app.di.presentationModule
import com.moviematcher.authentication.login.data.di.authModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieMatcher : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieMatcher)
            modules(presentationModule, authModule)
        }
    }
}