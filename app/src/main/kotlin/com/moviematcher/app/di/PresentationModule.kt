package com.moviematcher.app.di

import com.moviematcher.app.navigation.StartDestinationUseCase
import com.moviematcher.authentication.login.presentation.LoginScreenViewModel
import com.moviematcher.matching.presentation.match.MatcherViewModel
import com.moviematcher.matching.presentation.matched_list.MatchedResultViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    single { StartDestinationUseCase(get()) }

    //View models
    viewModel { LoginScreenViewModel(get()) }
    viewModel { MatchedResultViewModel(get()) }
    viewModel { MatcherViewModel(get()) }

}