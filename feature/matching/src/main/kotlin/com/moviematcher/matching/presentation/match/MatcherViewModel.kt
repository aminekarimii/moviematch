package com.moviematcher.matching.presentation.match

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviematcher.domain.repositories.SessionRepository
import com.moviematcher.domain.usecase.LoadMoviesBatchUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val MATCHING_TIME = 60

class MatcherViewModel(
    sessionRepository: SessionRepository,
    private val loadMoviesBatchUseCase: LoadMoviesBatchUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<MatcherViewState>(MatcherViewState.Loading)
    val viewState = _viewState.asStateFlow()

    private val timeLeft: MutableStateFlow<Int> = MutableStateFlow(MATCHING_TIME)
    private val counter: MutableStateFlow<Int> = MutableStateFlow(0)

    init {
        fetchRandomMovies()
        startTimer()
    }

    private fun startTimer() = viewModelScope.launch {
        while (timeLeft.value > 0) {
            delay(1000L)
            timeLeft.update { it - 1 }
            _viewState.update {
                if(it is MatcherViewState.Success) {
                    it.copy(timer = timeLeft.value)
                } else {
                    it
                }
            }
        }

        _viewState.update { MatcherViewState.MatchCompleted }
    }

    private fun fetchRandomMovies(shouldFetchNextPage: Boolean = false) = viewModelScope.launch {
        if (!shouldFetchNextPage) {
            _viewState.update { MatcherViewState.Loading }
        }

        val movies = loadMoviesBatchUseCase()

        _viewState.update {
            MatcherViewState.Success(
                matches = movies,
                counter = counter.value,
                timer = timeLeft.value
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