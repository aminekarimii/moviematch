package com.moviematcher.data.network

import com.moviematcher.data.dto.MovieResponse
import com.moviematcher.data.dto.MoviesResponse
import com.moviematcher.data.dto.VideoResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class MovieClient(private val httpClient: HttpClient) : MovieService {

    override suspend fun fetchMovie(id: Int): MovieResponse {
        return httpClient.get("movie/$id") {
            url {
                parameters.append("id", id.toString())
            }
        }.body()
    }

    override suspend fun fetchMovies(
        pageNumber: Int,
        options: Map<String, String>
    ): MoviesResponse {
        return httpClient.get("discover/movie") {
            url {
                parameters.append("page", pageNumber.toString())
                options.forEach {
                    parameter(it.key, it.value)
                }
            }
        }.body()
    }

    override suspend fun getMovieVideos(movieId: Int): VideoResponse {
        return httpClient.get("movie/$movieId/videos") {
            url {
                parameters.append("id", movieId.toString())
            }
        }.body()
    }
}