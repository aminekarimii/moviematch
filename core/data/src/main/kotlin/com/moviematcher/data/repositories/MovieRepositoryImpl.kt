package com.moviematcher.data.repositories

import com.moviematcher.data.dto.MovieResponse
import com.moviematcher.data.dto.toDomain
import com.moviematcher.data.network.MovieService
import com.moviematcher.domain.models.Movie
import com.moviematcher.domain.repositories.MovieRepository

class MovieRepositoryImpl(
    private val movieClient: MovieService,
) : MovieRepository {
    override suspend fun getMovies(pageNumber: Int): List<Movie> {
        val filters = mapOf(
            "sort_by" to "popularity.desc",
            "vote_average.gte" to 6.5f.toString(),
        )
        return movieClient.fetchMovies(
            pageNumber = pageNumber,
            options = filters
        ).movies.map(MovieResponse::toDomain)
    }
}