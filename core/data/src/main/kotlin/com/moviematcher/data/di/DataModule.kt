package com.moviematcher.data.di

import com.google.firebase.database.FirebaseDatabase
import com.moviematcher.data.network.MovieClient
import com.moviematcher.data.network.MovieService
import com.moviematcher.data.repositories.AuthRepositoryImpl
import com.moviematcher.data.repositories.MovieRepositoryImpl
import com.moviematcher.data.repositories.SessionRepositoryImpl
import com.moviematcher.domain.repositories.MovieRepository
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val movieClientModule = module {
    single<MovieService> { MovieClient(get<HttpClient>()) }
}

val repositoryModule = module {
    single<com.moviematcher.domain.repositories.AuthRepository> { AuthRepositoryImpl(get()) }
    single<MovieRepository> { MovieRepositoryImpl(get<MovieService>()) }
    single<com.moviematcher.domain.repositories.SessionRepository> {
        SessionRepositoryImpl(
            get<FirebaseDatabase>(),
            get<Json>()
        )
    }
}

val dataModule = module {
    includes(networkModule)
    includes(firebaseModule)
    includes(movieClientModule)
    includes(repositoryModule)
}