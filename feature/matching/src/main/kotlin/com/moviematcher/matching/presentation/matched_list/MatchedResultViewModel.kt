package com.moviematcher.matching.presentation.matched_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviematcher.domain.usecase.GetMatchedMoviesUseCase
import com.moviematcher.matching.navigation.MatcherScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class MatchedResultViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMatchedMoviesUseCase: GetMatchedMoviesUseCase
) : ViewModel() {

    private val sessionId =
        requireNotNull(savedStateHandle.get<String>(MatcherScreen.ARG_SESSION_ID)) {
            "The ARG_SESSION_ID should be passed in navigation !!"
        }

    private val _viewState = MutableSharedFlow<MatchedResultState>()
    val viewState: SharedFlow<MatchedResultState> = _viewState

    init {
        viewModelScope.launch {
            _viewState.emit(MatchedResultState.Loading)
            delay(1000L)
            val movies = getMatchedMoviesUseCase(sessionId)
            _viewState.emit(
                if (movies.isEmpty()) MatchedResultState.EmptyResult
                else MatchedResultState.MatchedResults(movies)
            )
        }
    }
}