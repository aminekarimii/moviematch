package com.moviematcher.domain.models

data class MatchSession(
    val host: String? = null,
    val guest: String? = null,
    val movies: List<Movie> = emptyList()
)