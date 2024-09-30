package com.moviematcher.domain.models


data class Movie(
    val index: Int = 0,
    val id: Int,
    val firstAirDate: String? = "",
    val name: String,
    val title: String,
    val originalLanguage: String,
    val overview: String,
    val posterPath: String?,
    val voteAverage: Double,
    val voteCount: Int,
)