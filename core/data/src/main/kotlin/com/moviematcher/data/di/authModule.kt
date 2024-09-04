package com.moviematcher.data.di

import com.google.firebase.auth.FirebaseAuth
import com.moviematcher.data.repositories.AuthRepositoryImpl
import com.moviematcher.domain.repositories.AuthRepository
import org.koin.dsl.module

val dataModule = module {
    single<FirebaseAuth> { FirebaseAuth.getInstance() }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}