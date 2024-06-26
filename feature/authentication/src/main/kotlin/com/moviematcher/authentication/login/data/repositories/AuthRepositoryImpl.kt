package com.moviematcher.authentication.login.data.repositories

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.moviematcher.authentication.login.domain.models.Account
import com.moviematcher.authentication.login.domain.repositories.AuthRepository
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val signInClient: GoogleSignInClient
) : AuthRepository {

    override suspend fun login(account: Account) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).await()
    }

    override fun logout() {
        firebaseAuth.signOut()
        signInClient.signOut()
    }
}