package com.moviematcher.domain.repositories

import com.moviematcher.domain.models.Account
import com.moviematcher.domain.models.User

interface AuthRepository {
    suspend fun login(account: Account)
    suspend fun loginAsGuest()
    fun getCurrentUser(): User?
    fun logout()
}