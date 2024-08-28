package com.moviematcher.matching.presentation.matched_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class MatchedResultViewModel : ViewModel() {
    private val demoMovie = MovieModel(
        index = 1,
        title = "Money Heist - La casa de papel 2017 from Netflix",
        year = "2021",
        length = "2h 30m",
        posterUrl = "https://image.tmdb.org/t/p/w500/6MKr3KgOLmzOP6MSuZERO41Lpkt.jpg"
    )

    private val _viewState = MutableSharedFlow<MatchedResultState>()
    val viewState: SharedFlow<MatchedResultState> = _viewState

    init {
        viewModelScope.launch {
            _viewState.emit(MatchedResultState.Loading)
            delay(500)
            _viewState.emit(
                MatchedResultState.MatchedResults(
                    movies = listOf(demoMovie, demoMovie, demoMovie, demoMovie)
                )
            )
        }
    }
}