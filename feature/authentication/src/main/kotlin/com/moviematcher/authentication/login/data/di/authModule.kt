package com.moviematcher.authentication.login.data.di

import com.google.firebase.auth.FirebaseAuth
import com.moviematcher.authentication.login.data.repositories.AuthRepositoryImpl
import com.moviematcher.authentication.login.domain.repositories.AuthRepository
import org.koin.dsl.module

val authModule = module {
    single<FirebaseAuth> { FirebaseAuth.getInstance() }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
}