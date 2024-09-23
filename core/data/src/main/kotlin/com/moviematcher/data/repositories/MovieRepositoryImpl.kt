package com.moviematcher.data.repositories

import com.moviematcher.data.network.MovieClient
import com.moviematcher.domain.models.Movie
import com.moviematcher.domain.repositories.MovieRepository

class MovieRepositoryImpl(
    private val movieClient: MovieClient,
) : MovieRepository {
    override suspend fun getMovies(): List<Movie> {
        // TODO implement a mapping mechanism to convert the DTO to the domain model
        return movieClient.fetchMovies(
            pageNumber = 1,
            options = mapOf(
                "sort_by" to "popularity.desc"
            )
        ).movies.map {
            Movie(
                id = it.id,
                name = it.name,
                title = it.originalTitle,
                overview = it.overview,
                posterPath = it.posterPath,
                voteAverage = it.voteAverage,
                originalLanguage = it.originalLanguage,
                voteCount = it.voteCount,
                firstAirDate = it.firstAirDate,
                index = 0
            )
        }
    }
}