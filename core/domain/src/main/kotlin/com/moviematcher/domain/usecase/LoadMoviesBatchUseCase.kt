package com.moviematcher.domain.usecase

import com.moviematcher.domain.models.Movie
import com.moviematcher.domain.repositories.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

const val PAGES_TO_PULL = 6

class LoadMoviesBatchUseCase(
    private val movieRepository: MovieRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): List<Movie> {
        return withContext(dispatcher) {
            val deferredMovieCalls = (1 until PAGES_TO_PULL).map { page ->
                async { movieRepository.getMovies(page) }.await()
            }

            deferredMovieCalls.flatten()
        }
    }
}