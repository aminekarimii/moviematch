package com.moviematcher.domain.di

import com.moviematcher.domain.usecase.JoinSessionUseCase
import com.moviematcher.domain.usecase.LoadMoviesBatchUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        com.moviematcher.domain.usecase.GetMatchedMoviesUseCase(
            get(),
            get(),
            Dispatchers.IO
        )
    }
    factory { JoinSessionUseCase(get(), get(), Dispatchers.IO) }
    factory { LoadMoviesBatchUseCase(get(), Dispatchers.IO) }
}