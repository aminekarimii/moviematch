package com.moviematcher.app.navigation

import com.moviematcher.domain.repositories.AuthRepository


class StartDestinationUseCase(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(): String {
        val isConnected = authRepository.getCurrentUser() != null
        return if (isConnected) Screen.SESSION.name else Screen.AUTH.name
    }
}