package com.moviematcher.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.moviematcher.domain.models.User
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
) : com.moviematcher.domain.repositories.AuthRepository {

    override suspend fun login(account: com.moviematcher.domain.models.Account) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).await()
    }

    override suspend fun loginAsGuest() {
        try {
            firebaseAuth.signInAnonymously().await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
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