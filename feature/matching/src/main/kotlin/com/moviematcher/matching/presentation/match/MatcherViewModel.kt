package com.moviematcher.matching.presentation.match

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviematcher.domain.models.Movie
import com.moviematcher.domain.repositories.MovieRepository
import com.moviematcher.domain.usecase.LoadMoviesBatchUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val MIN_MOVIES_TO_FETCH_NEXT_PAGE = 10

class MatcherViewModel(
    private val movieRepository: MovieRepository,
    private val loadMoviesBatchUseCase: LoadMoviesBatchUseCase
) : ViewModel() {
    private var pageNumber = 1

    private val _viewState = MutableStateFlow<MatcherViewState>(MatcherViewState.Loading)
    val viewState = _viewState.asStateFlow()

    private val counter: MutableStateFlow<Int> = MutableStateFlow(0)
    private val fetchedMovieIds = mutableSetOf<String>()

    init {
        viewModelScope.launch {
            val movies = loadMoviesBatchUseCase.invoke()
            Log.d("SpecialTag", movies.map { it.id }.toString())
        }
        fetchRandomMovies()
    }

    private fun fetchRandomMovies(shouldFetchNextPage: Boolean = false) = viewModelScope.launch {
        if (!shouldFetchNextPage) {
            _viewState.update { MatcherViewState.Loading }
        }

        val fetchNextPage = if (shouldFetchNextPage) ++pageNumber else pageNumber
        val movies = movieRepository.getMovies(fetchNextPage)

        val uniqueMovies = movies.filter { movie ->
            !fetchedMovieIds.contains(movie.id.toString())
        }

        fetchedMovieIds.addAll(uniqueMovies.map { it.id.toString() })

        _viewState.update { currentState ->
            when (currentState) {
                is MatcherViewState.Success -> {
                    MatcherViewState.Success(
                        matches = if (shouldFetchNextPage) currentState.matches + uniqueMovies else uniqueMovies,
                        counter = counter.value
                    )
                }

                else -> MatcherViewState.Success(matches = uniqueMovies, counter = counter.value)
            }
        }
    }

    fun swipeLatestMovie(updateCounter: Boolean) {
        _viewState.update { currentState ->
            if (currentState is MatcherViewState.Success) {
                if (currentState.matches.size == 1) {
                    MatcherViewState.MatchCompleted
                } else {
                    if (updateCounter) {
                        counter.update { it + 1 }
                    }

                    val updatedMatches = currentState.matches.dropLast(1)
                    if (updatedMatches.size <= MIN_MOVIES_TO_FETCH_NEXT_PAGE) {
                        fetchRandomMovies(shouldFetchNextPage = true)
                    }

                    currentState.copy(
                        matches = updatedMatches,
                        counter = counter.value
                    )
                }
            } else {
                currentState
            }
        }
    }
}

@Immutable
sealed class MatcherViewState {
    data object Loading : MatcherViewState()
    data object MatchCompleted : MatcherViewState()
    data class Error(val message: String) : MatcherViewState()

    data class Success(
        val matches: List<Movie>,
        val counter: Int
    ) : MatcherViewState()
}