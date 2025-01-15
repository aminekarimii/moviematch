package com.moviematcher.domain.repositories

import com.moviematcher.domain.models.MatchSession
import com.moviematcher.domain.models.Movie
import com.moviematcher.domain.models.SessionQuery
import kotlinx.coroutines.flow.Flow
import java.sql.Timestamp

interface SessionRepository {

    fun getSession(sessionId: String): Flow<Result<MatchSession>>
    fun isGuestReady(sessionId: String): Flow<Result<Boolean>>
    fun getMatchStatus(sessionId: String): Flow<Result<Int>>

    suspend fun createNewSession(
        createdAt: Timestamp? = null,
        sessionId: String,
        hostId: String?,
        movies: List<Movie>
    )

    suspend fun updateSession(sessionQuery: SessionQuery)
}