package com.moviematcher.domain.repositories

import com.moviematcher.domain.models.Movie

interface MovieRepository {

    suspend fun getMovies(pageNumber: Int): List<Movie>
    suspend fun getMovie(id: Int): Movie
}