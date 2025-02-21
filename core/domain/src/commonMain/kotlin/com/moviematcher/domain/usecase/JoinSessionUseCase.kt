package com.moviematcher.domain.usecase

import com.moviematcher.domain.models.SessionQuery
import com.moviematcher.domain.models.SessionStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

class JoinSessionUseCase(
    private val sessionRepository: com.moviematcher.domain.repositories.SessionRepository,
    private val authRepository: com.moviematcher.domain.repositories.AuthRepository,
    private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(sessionId: String): JoinSessionResult {
        return withContext(dispatcher) {
            val sessionResponse = sessionRepository.getSession(sessionId).first()

            if (sessionResponse.isSuccess) {
                val session = sessionResponse.getOrNull()
                session?.let {
                    return@withContext when (it.status) {
                        SessionStatus.WAITING -> {
                            sessionRepository.updateSession(
                                SessionQuery(
                                    updatedAt = Clock.System.now().toEpochMilliseconds(),
                                    sessionId = sessionId,
                                    guestId = authRepository.getCurrentUser()?.uuid,
                                    status = SessionStatus.ACTIVE
                                )
                            )
                            JoinSessionResult.Success
                        }
                        SessionStatus.COMPLETED -> JoinSessionResult.SessionCompleted
                        SessionStatus.ACTIVE -> JoinSessionResult.SessionActive
                        else -> JoinSessionResult.SessionNotFound
                    }
                }
            } else if (sessionResponse.isFailure) {
                return@withContext JoinSessionResult.ServerError
            }

            JoinSessionResult.SessionNotFound
        }
    }

}

sealed class JoinSessionResult {
    data object Success : JoinSessionResult()
    data object SessionNotFound : JoinSessionResult()
    data object SessionCompleted : JoinSessionResult()
    data object SessionActive : JoinSessionResult()
    data object ServerError : JoinSessionResult()
}
