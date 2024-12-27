package com.moviematcher.domain.models

import java.sql.Timestamp

data class SessionQuery(
        val updatedAt: Timestamp = Timestamp(System.currentTimeMillis()),
        val sessionId: String,
        val isHostReady: Boolean? = null,
        val guestId: String? = null,
        val movies: List<Movie>? = null,
        val guestLikedMovies: List<Int>? = null,
        val hostLikedMovies: List<Int>? = null
    )