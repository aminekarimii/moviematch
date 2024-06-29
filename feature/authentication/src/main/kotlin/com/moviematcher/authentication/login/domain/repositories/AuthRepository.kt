package com.moviematcher.authentication.login.domain.repositories

import com.moviematcher.authentication.login.domain.models.Account
import com.moviematcher.authentication.login.domain.models.User

interface AuthRepository {
    suspend fun login(account: Account)
    fun getCurrentUser(): User?
    fun logout()
}