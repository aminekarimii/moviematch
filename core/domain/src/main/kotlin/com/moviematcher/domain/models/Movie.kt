package com.moviematcher.domain.models


data class Movie(
    val index: Int = 0,
    val id: Int,
    val year: String? = "",
    val name: String,
    val title: String,
    val originalLanguage: String,
    val overview: String,
    val posterUrl: String?,
    val voteAverage: Double,
    val voteCount: Int,
)