package com.moviematcher.data.repositories

import com.google.firebase.database.FirebaseDatabase
import com.moviematcher.data.FirebaseDatabaseNodes.GUEST
import com.moviematcher.data.FirebaseDatabaseNodes.GUEST_LIKES
import com.moviematcher.data.FirebaseDatabaseNodes.HOST
import com.moviematcher.data.FirebaseDatabaseNodes.HOST_LIKES
import com.moviematcher.data.FirebaseDatabaseNodes.MOVIES
import com.moviematcher.data.FirebaseDatabaseNodes.SESSIONS
import com.moviematcher.data.FirebaseDatabaseNodes.SESSION_STATUS
import com.moviematcher.data.dto.SessionDto
import com.moviematcher.data.dto.toMatchSession
import com.moviematcher.domain.models.MatchSession
import com.moviematcher.domain.models.SessionQuery
import com.moviematcher.domain.models.SessionStatus
import com.skydoves.firebase.database.ktx.flow
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import java.sql.Timestamp

class SessionRepositoryImpl(
    private val database: FirebaseDatabase,
    private val json: Json,
) : com.moviematcher.domain.repositories.SessionRepository {

    override fun getSession(sessionId: String): Flow<Result<MatchSession>> {
        return database.reference.apply {
            this.limitToFirst(10)
        }.flow<SessionDto>(
            path = { dataSnapshot ->
                dataSnapshot.child(SESSIONS).child(sessionId)
            },
            decodeProvider = {
                json.decodeFromString(it)
            }
        ).map { result ->
            when {
                result.isSuccess && result.getOrNull() != null -> Result.success(
                    result.getOrNull()!!.toMatchSession()
                )

                else -> Result.failure(Exception("Error getting session"))
            }
        }
    }

    override fun isGuestReady(sessionId: String): Flow<Result<Boolean>> {
        return this.getSession(sessionId).map {
            when {
                it.isSuccess -> Result.success(it.getOrNull()?.guestId != null)
                else -> Result.failure(it.exceptionOrNull()!!)
            }
        }
    }

    override fun getMatchStatus(sessionId: String): Flow<Result<Int>> {
        return database.reference.apply {
            this.limitToFirst(10)
        }.flow<SessionDto>(
            path = { dataSnapshot ->
                dataSnapshot.child(SESSIONS).child(sessionId)
            },
            decodeProvider = {
                json.decodeFromString(it)
            }
        ).map {
            when {
                it.isSuccess -> {
                    val (hostLikes, guestLikes) = it.getOrNull()?.guestLikes.orEmpty() to it.getOrNull()?.hostLikes.orEmpty()
                    // Find common movie IDs
                    val commonLikedMovieIds = guestLikes
                        .toSet()
                        .intersect(hostLikes.toSet())

                    Result.success(commonLikedMovieIds.size)
                }

                else -> Result.failure(it.exceptionOrNull()!!)
            }
        }
    }

    override suspend fun createNewSession(
        sessionId: String,
        hostId: String?,
    ) {
        database.reference
            .child(SESSIONS)
            .child(sessionId).apply {
                val host = child(HOST).setValue(hostId)
                    .asDeferred()
                val sessionStatus = child(SESSION_STATUS)
                    .setValue(SessionStatus.WAITING)
                    .asDeferred()
                val createdAtResult = child("createdAt")
                    .setValue(Timestamp(System.currentTimeMillis()).time)
                    .asDeferred()

                listOfNotNull(createdAtResult, host,sessionStatus).awaitAll()
            }
    }

    override suspend fun updateSession(sessionQuery: SessionQuery) {
        with(sessionQuery) {
            database.reference.child(SESSIONS)
                .child(sessionId)
                .apply {
                    val guestId = guestId?.let { child(GUEST).setValue(it).asDeferred() }

                    val updatedAtResult = updatedAt.let {
                        child("updatedAt").setValue(it).asDeferred()
                    }

                    val guestLikedMoviesResult = guestLikedMovies?.let {
                        child(GUEST_LIKES)
                            .setValue(guestLikedMovies)
                            .asDeferred()
                    }

                    val sessionStatus = status?.let {
                        child(SESSION_STATUS)
                            .setValue(status)
                            .asDeferred()
                    }


                    val hostLikedMoviesResult = hostLikedMovies?.let {
                        child(HOST_LIKES)
                            .setValue(hostLikedMovies)
                            .asDeferred()
                    }
                    val moviesResult = movies?.let {
                        child(MOVIES)
                            .setValue(json.encodeToJsonElement(movies).toString())
                            .asDeferred()
                    }

                    listOfNotNull(
                        guestId,
                        updatedAtResult,
                        guestLikedMoviesResult,
                        hostLikedMoviesResult,
                        sessionStatus,
                        moviesResult
                    ).awaitAll()
                }
        }
    }
}