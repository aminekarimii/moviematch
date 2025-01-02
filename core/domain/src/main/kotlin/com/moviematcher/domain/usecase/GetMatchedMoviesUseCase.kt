package com.moviematcher.domain.usecase

import com.moviematcher.domain.models.Movie
import com.moviematcher.domain.repositories.MovieRepository
import com.moviematcher.domain.repositories.SessionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class GetMatchedMoviesUseCase(
    private val sessionRepository: SessionRepository,
    private val movieRepository: MovieRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(sessionId: String): List<Movie> {
        return withContext(dispatcher) {
            val session = sessionRepository.getSession(sessionId).first()

            if (session.isSuccess) {
                val guestMovies = session.getOrNull()?.guestLikes ?: emptyList()
                val hostMovies = session.getOrNull()?.hostLikes ?: emptyList()


                val deferredMovieCalls = guestMovies.union(hostMovies).toList().map { id ->
                    async {
                        movieRepository.getMovie(id).let {
                            val movieTrailer = movieRepository.getMovieTrailer(id).asTrailer()
                            it.copy(trailerUrl = movieTrailer)
                        }
                    }
                }

                return@withContext deferredMovieCalls.awaitAll()
            }

            emptyList()
        }
    }

    private fun String?.asTrailer(): String? {
        return this?.let { "https://www.youtube.com/watch?v=$it" }
    }
}