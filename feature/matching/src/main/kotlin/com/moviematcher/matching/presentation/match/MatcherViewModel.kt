package com.moviematcher.matching.presentation.match

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviematcher.matching.presentation.matched_list.MovieModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MatcherViewModel : ViewModel() {
    private val demoMovie = MovieModel(
        index = 1,
        title = "Money Heist - La casa de papel 2017 from Netflix",
        year = "2021",
        length = "2h 30m",
        posterUrl = "https://image.tmdb.org/t/p/w500/6MKr3KgOLmzOP6MSuZERO41Lpkt.jpg",
        rating = 4.5f
    )

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
                matches = List(3) { index ->
                    demoMovie.copy(index = index + 1)
                },
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
    data class Success(val matches: List<MovieModel>, val counter: Int) : MatcherViewState()
    data class Error(val message: String) : MatcherViewState()
}