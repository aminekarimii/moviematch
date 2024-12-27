package com.moviematcher.domain.models

data class MatchSession(
    val hostId: String? = null,
    val guestId: String? = null,
    val movies: List<Movie> = emptyList(),
    val guestLikes: List<Int> = emptyList(),
    val hostLikes: List<Int> = emptyList()
)