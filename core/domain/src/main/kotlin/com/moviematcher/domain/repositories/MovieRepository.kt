package com.moviematcher.domain.repositories

import com.moviematcher.domain.models.Movie

interface MovieRepository {

    suspend fun getMovies(): List<Movie>
}