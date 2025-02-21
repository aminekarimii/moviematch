package com.moviematcher.domain.repositories

import com.moviematcher.domain.models.MatchSession
import com.moviematcher.domain.models.SessionQuery
import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    fun getSession(sessionId: String): Flow<Result<MatchSession>>
    fun isGuestReady(sessionId: String): Flow<Result<Boolean>>
    fun getMatchStatus(sessionId: String): Flow<Result<Int>>

    suspend fun createNewSession(
        sessionId: String,
        hostId: String?,
    )

    suspend fun updateSession(sessionQuery: SessionQuery)
}