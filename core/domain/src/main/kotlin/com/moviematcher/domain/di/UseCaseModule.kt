package com.moviematcher.domain.di

import com.moviematcher.domain.usecase.LoadMoviesBatchUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val useCaseModule = module {
    factory { LoadMoviesBatchUseCase(get(), Dispatchers.IO) }
}