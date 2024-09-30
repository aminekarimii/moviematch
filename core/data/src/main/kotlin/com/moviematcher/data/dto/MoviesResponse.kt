package com.moviematcher.data.dto

import com.moviematcher.domain.models.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    @SerialName("page") val page: Int,
    @SerialName("results") val movies: List<MovieResponse>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int,
)

@Serializable
data class MovieResponse(
    @SerialName("id") val id: Int,
    @SerialName("release_date") val firstAirDate: String? = "",
    @SerialName("title") val name: String,
    @SerialName("original_title") val originalTitle: String,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("overview") val overview: String,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int,
) {
    val posterImageUrl = "https://image.tmdb.org/t/p/w500${posterPath.orEmpty()}"
}

fun MovieResponse.toDomain() = Movie(
    id = id,
    name = name,
    title = originalTitle,
    overview = overview,
    posterUrl = posterImageUrl,
    voteAverage = voteAverage,
    originalLanguage = originalLanguage,
    voteCount = voteCount,
    firstAirDate = firstAirDate,
)
