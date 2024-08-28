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
    private var id = 0

    private val demoMovie = MovieModel(
        index = 1,
        title = "Money Heist - La casa de papel 2017 from Netflix",
        year = "2021",
        length = "2h 30m",
        posterUrl = "https://image.tmdb.org/t/p/w500/6MKr3KgOLmzOP6MSuZERO41Lpkt.jpg"
    )

    private val _viewState = MutableStateFlow<MatcherViewState>(MatcherViewState.Loading)
    val viewState = _viewState.asStateFlow()

    init {
        fetchRandomMovies()
    }
    private fun generateMoviesWithIncrementedId(count: Int): List<MovieModel> {
        return List(count) { index ->
            demoMovie.copy(index = index + 1)
        }
    }

    fun fetchRandomMovies() = viewModelScope.launch {
        _viewState.update { MatcherViewState.Loading }

        _viewState.update {
            MatcherViewState.Success(
                matches =generateMoviesWithIncrementedId(4)

            )
        }
    }

    fun removeLastProfile() {
        _viewState.update {
            if (it is MatcherViewState.Success) {
                it.copy(matches = it.matches.dropLast(1))
            } else it
        }
    }

}

@Immutable
sealed class MatcherViewState {
    data object Loading : MatcherViewState()
    data class Success(val matches: List<MovieModel>) : MatcherViewState()
    data class Error(val message: String) : MatcherViewState()
}