package com.moviematcher.data.di

import com.moviematcher.data.network.MovieClient
import com.moviematcher.data.network.MovieService
import com.moviematcher.data.repositories.AuthRepositoryImpl
import com.moviematcher.data.repositories.MovieRepositoryImpl
import com.moviematcher.domain.repositories.AuthRepository
import com.moviematcher.domain.repositories.MovieRepository
import io.ktor.client.HttpClient
import org.koin.dsl.module


val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<MovieRepository> { MovieRepositoryImpl(get()) }
}
val movieClientModule = module {
    single<MovieService> { MovieClient(get<HttpClient>()) }
}

val dataModule = module {
    includes(networkModule)
    includes(movieClientModule)
    includes(authModule)
    includes(repositoryModule)
}