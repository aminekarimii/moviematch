package com.moviematcher.authentication.login.domain.repositories

import com.moviematcher.authentication.login.domain.models.Account

interface AuthRepository {
    suspend fun login(account: Account)
    fun logout()
}