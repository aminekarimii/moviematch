package com.moviematcher.matching.presentation.matched_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviematcher.domain.usecase.GetMatchedMoviesUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class MatchedResultViewModel(
    private val getMatchedMoviesUseCase: GetMatchedMoviesUseCase
) : ViewModel() {

    private val _viewState = MutableSharedFlow<MatchedResultState>()
    val viewState: SharedFlow<MatchedResultState> = _viewState

    init {
        viewModelScope.launch {
            _viewState.emit(MatchedResultState.Loading)
            delay(1000L)
            val movies = getMatchedMoviesUseCase("05aTQJ8S7nr")
            _viewState.emit(
                if (movies.isEmpty()) MatchedResultState.EmptyResult
                else MatchedResultState.MatchedResults(movies)
            )
        }
    }
}