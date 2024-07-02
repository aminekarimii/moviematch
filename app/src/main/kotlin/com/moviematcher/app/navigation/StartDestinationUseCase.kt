package com.moviematcher.app.navigation

import com.moviematcher.authentication.login.domain.repositories.AuthRepository

class StartDestinationUseCase(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(): String {
        val isConnected = authRepository.getCurrentUser() != null

        return if (isConnected) Screen.HOME.route else Screen.AUTH.route
    }
}