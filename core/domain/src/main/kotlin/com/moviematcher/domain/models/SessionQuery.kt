package com.moviematcher.domain.models

import kotlinx.datetime.Clock

data class SessionQuery(
    val updatedAt: Long = Clock.System.now().toEpochMilliseconds(),
    val sessionId: String,
    val isHostReady: Boolean? = null,
    val guestId: String? = null,
    val movies: List<Movie>? = null,
    val guestLikedMovies: List<Int>? = null,
    val hostLikedMovies: List<Int>? = null
)