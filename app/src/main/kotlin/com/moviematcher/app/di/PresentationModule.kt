package com.moviematcher.app.di

import com.moviematcher.app.navigation.StartDestinationUseCase
import com.moviematcher.authentication.login.presentation.LoginScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    //View models
    viewModel { LoginScreenViewModel(get()) }

    single { StartDestinationUseCase(get()) }
}