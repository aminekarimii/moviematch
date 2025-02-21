package com.moviematcher.app.navigation


class StartDestinationUseCase(
    private val authRepository: com.moviematcher.domain.repositories.AuthRepository,
) {
    operator fun invoke(): String {
        val isConnected = authRepository.getCurrentUser() != null
        return if (isConnected) Screen.SESSION.name else Screen.AUTH.name
    }
}