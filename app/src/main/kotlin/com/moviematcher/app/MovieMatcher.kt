package com.moviematcher.app

import android.app.Application
import com.moviematcher.app.di.presentationModule
import com.moviematcher.data.di.dataModule
import com.moviematcher.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieMatcher : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieMatcher)
            modules(dataModule, presentationModule, domainModule)
        }
    }
}