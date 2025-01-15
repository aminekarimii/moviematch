package com.moviematcher.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.koin.dsl.module

val firebaseModule = module {
    single<FirebaseAuth> { FirebaseAuth.getInstance() }
    single { FirebaseDatabase.getInstance() }
}