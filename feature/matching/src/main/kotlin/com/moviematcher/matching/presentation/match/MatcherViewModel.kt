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


class MatcherViewModel(
    private val loadMoviesBatchUseCase: LoadMoviesBatchUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<MatcherViewState>(MatcherViewState.Loading)
    val viewState = _viewState.asStateFlow()

    private val counter: MutableStateFlow<Int> = MutableStateFlow(0)

    init {
        fetchRandomMovies()
    }

    private fun fetchRandomMovies(shouldFetchNextPage: Boolean = false) = viewModelScope.launch {
        if (!shouldFetchNextPage) {
            _viewState.update { MatcherViewState.Loading }
        }

        val movies = loadMoviesBatchUseCase()

        _viewState.update {
            MatcherViewState.Success(
                matches = movies,
                counter = counter.value
            )
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