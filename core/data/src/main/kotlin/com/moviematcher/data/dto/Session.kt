package com.moviematcher.data.dto

import com.google.firebase.database.Exclude
import com.moviematcher.domain.models.Movie
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

@Serializable
data class SessionDto(
    val host: String? = null,
    val guest: String? = null,
    val movies: String? = null
) {
    @Exclude
    fun getMoviesList() = this.movies?.let { movies ->
        Json.decodeFromString(ListSerializer(Movie.serializer()), movies)
    } ?: emptyList()
}

fun SessionDto.toMatchSession() = com.moviematcher.domain.models.MatchSession(
    host = this.host ?: "",
    guest = this.guest ?: "",
    movies = this.getMoviesList()
)
