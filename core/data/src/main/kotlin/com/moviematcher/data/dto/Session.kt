package com.moviematcher.data.dto

import com.google.firebase.database.Exclude
import com.moviematcher.domain.models.MatchSession
import com.moviematcher.domain.models.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

@Serializable
data class SessionDto(
    val host: String? = null,
    val guest: String? = null,
    val movies: String? = null,
    @SerialName("guest_likes") val guestLikes: List<String>? = emptyList(),
    @SerialName("host_likes") val hostLikes: List<String>? = emptyList()
) {
    @Exclude
    fun getMoviesList() = this.movies?.let { movies ->
        Json.decodeFromString(ListSerializer(Movie.serializer()), movies)
    } ?: emptyList()
}

fun SessionDto.toMatchSession() = MatchSession(
    hostId = this.host,
    guestId = this.guest,
    movies = this.getMoviesList(),
    guestLikes = this.guestLikes?.map { it.toInt() } ?: emptyList(),
    hostLikes = this.hostLikes?.map { it.toInt() } ?: emptyList()
)
