package com.moviematcher.domain.models

data class MatchSession(
    val hostId: String? = null,
    val guestId: String? = null,
    val status: SessionStatus? = null,
    val movies: List<Movie> = emptyList(),
    val guestLikes: List<Int> = emptyList(),
    val hostLikes: List<Int> = emptyList()
)

enum class SessionStatus {
    WAITING, ACTIVE, COMPLETED, ARCHIVED
}