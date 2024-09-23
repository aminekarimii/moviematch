package com.moviematcher.domain.models


data class Movie(
    val index: Int,
    val id: Int,
    val firstAirDate: String? = "",
    val name: String,
    val title: String,
    val originalLanguage: String,
    val overview: String,
    val posterPath: String?,
    val voteAverage: Double,
    val voteCount: Int,
) {
    fun getPosterUrl(): String {
        return "https://image.tmdb.org/t/p/w500${posterPath.orEmpty()}"
    }
}