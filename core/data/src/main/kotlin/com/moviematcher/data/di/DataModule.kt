package com.moviematcher.data.di

import com.moviematcher.data.network.MovieClient
import com.moviematcher.data.network.MovieService
import com.moviematcher.data.repositories.AuthRepositoryImpl
import com.moviematcher.data.repositories.MovieRepositoryImpl
import com.moviematcher.domain.repositories.AuthRepository
import com.moviematcher.domain.repositories.MovieRepository
import io.ktor.client.HttpClient
import org.koin.dsl.module

val movieClientModule = module {
    single<MovieService> { MovieClient(get<HttpClient>()) }
}

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<MovieRepository> { MovieRepositoryImpl(get<MovieService>()) }
}

val dataModule = module {
    includes(authModule)
    includes(networkModule)
    includes(movieClientModule)
    includes(repositoryModule)
}