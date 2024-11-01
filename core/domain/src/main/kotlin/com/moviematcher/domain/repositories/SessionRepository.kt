package com.moviematcher.domain.repositories

import com.moviematcher.domain.models.MatchSession
import com.moviematcher.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    fun getSession(sessionId: String): Flow<Result<MatchSession>>
    fun isGuestReady(sessionId: String): Flow<Result<Boolean>>

    suspend fun createNewSession(sessionId: String, movies: List<Movie>)
    suspend fun updateSession(
        sessionId: String,
        host: Boolean? = null,
        guest: Boolean? = null,
        movies: List<Movie>? = null
    )
}