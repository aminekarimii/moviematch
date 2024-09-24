package com.moviematcher.matching.presentation.match

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviematcher.domain.models.Movie
import com.moviematcher.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MatcherViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow<MatcherViewState>(MatcherViewState.Loading)
    val viewState = _viewState.asStateFlow()

    private val counter: MutableStateFlow<Int> = MutableStateFlow(0)

    init {
        fetchRandomMovies()
    }

    private fun fetchRandomMovies() = viewModelScope.launch {
        _viewState.update { MatcherViewState.Loading }
        _viewState.update {
            MatcherViewState.Success(
                matches = movieRepository.getMovies(),
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

                    currentState.copy(
                        matches = currentState.matches.dropLast(1),
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
    data class Success(val matches: List<Movie>, val counter: Int) : MatcherViewState()
    data class Error(val message: String) : MatcherViewState()
}