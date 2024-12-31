package com.moviematcher.data.network

import com.moviematcher.data.dto.MovieResponse
import com.moviematcher.data.dto.MoviesResponse
import com.moviematcher.data.dto.VideoResponse

interface MovieService {
    suspend fun fetchMovie(id: Int): MovieResponse
    suspend fun fetchMovies(pageNumber: Int, options: Map<String, String>): MoviesResponse
    suspend fun getMovieVideos(movieId: Int): VideoResponse
}
