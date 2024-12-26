package com.moviematcher.domain.repositories

import com.moviematcher.domain.models.MatchSession
import com.moviematcher.domain.models.Movie
import kotlinx.coroutines.flow.Flow
import java.sql.Timestamp

interface SessionRepository {

    fun getSession(sessionId: String): Flow<Result<MatchSession>>
    fun isGuestReady(sessionId: String): Flow<Result<Boolean>>

    suspend fun createNewSession(
        createdAt: Timestamp? = null,
        sessionId: String,
        movies: List<Movie>
    )

    suspend fun updateSession(
        updatedAt: Timestamp? = null,
        sessionId: String,
        isHostReady: Boolean? = null,
        isGuestReady: Boolean? = null,
        movies: List<Movie>? = null
    )
}