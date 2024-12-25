package com.moviematcher.data.repositories

import com.google.firebase.database.FirebaseDatabase
import com.moviematcher.data.FirebaseDatabaseNodes.GUEST
import com.moviematcher.data.FirebaseDatabaseNodes.HOST
import com.moviematcher.data.FirebaseDatabaseNodes.MOVIES
import com.moviematcher.data.FirebaseDatabaseNodes.SESSIONS
import com.moviematcher.data.dto.SessionDto
import com.moviematcher.data.dto.toMatchSession
import com.moviematcher.domain.models.MatchSession
import com.moviematcher.domain.models.Movie
import com.moviematcher.domain.repositories.SessionRepository
import com.skydoves.firebase.database.ktx.flow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

class SessionRepositoryImpl(
    private val database: FirebaseDatabase,
    private val json: Json,
) : SessionRepository {

    override fun getSession(sessionId: String): Flow<Result<MatchSession>> {
        return database.reference.flow<SessionDto>(
            path = { dataSnapshot ->
                dataSnapshot.child(SESSIONS).child(sessionId)
            },
            decodeProvider = {
                json.decodeFromString(it)
            }
        ).map {
            when {
                it.isSuccess -> Result.success(it.getOrNull()!!.toMatchSession())
                else -> Result.failure(it.exceptionOrNull()!!)
            }
        }
    }

    override fun isGuestReady(sessionId: String): Flow<Result<Boolean>> {
        return this.getSession(sessionId).map {
            when {
                it.isSuccess -> Result.success(it.getOrNull()?.guest == "ready")
                else -> Result.failure(it.exceptionOrNull()!!)
            }
        }
    }

    override suspend fun createNewSession(sessionId: String, movies: List<Movie>) {
        database.reference.child(SESSIONS)
            .child(sessionId)
            .apply {
                child(HOST).setValue("ready").await()
                child(GUEST).setValue(null).await()
                child(MOVIES).setValue(json.encodeToJsonElement(movies).toString()).await()
            }
    }

    override suspend fun updateSession(
        sessionId: String,
        isHostReady: Boolean?,
        isGuestReady: Boolean?,
        movies: List<Movie>?
    ) {
        database.reference.child(SESSIONS)
            .child(sessionId)
            .apply {
                isHostReady?.let { child(HOST).setValue("ready").await() }
                isGuestReady?.let { child(GUEST).setValue("ready").await() }
                movies?.let {
                    child(MOVIES)
                        .setValue(json.encodeToJsonElement(movies).toString())
                        .await()
                }
            }
    }


}