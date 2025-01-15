package com.moviematcher.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.moviematcher.domain.models.Account
import com.moviematcher.domain.models.User
import com.moviematcher.domain.repositories.AuthRepository
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
) : AuthRepository {

    override suspend fun login(account: Account) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).await()
    }

    override suspend fun loginAsGuest() {
        firebaseAuth.signInAnonymously().await()
    }

    override fun getCurrentUser(): User? {
        return firebaseAuth.currentUser?.let { User(
            uuid = it.uid,
            email = it.email
        ) }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}