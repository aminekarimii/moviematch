package com.moviematcher.data.network

import com.moviematcher.data.dto.MoviesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class MovieClient(val httpClient: HttpClient) : MovieService {

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
}