package com.moviematcher.data.network

import com.moviematcher.data.dto.MovieResponse
import com.moviematcher.data.dto.MoviesResponse

interface MovieService {
    suspend fun fetchMovie(id: Int): MovieResponse
    suspend fun fetchMovies(pageNumber: Int, options: Map<String, String>): MoviesResponse
}
