package com.moviematcher.matching.presentation.matched_list

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviematcher.matching.navigation.MatcherScreen
import com.moviematcher.session.SnackbarController
import com.moviematcher.session.SnackbarEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class MatchedResultViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMatchedMoviesUseCase: com.moviematcher.domain.usecase.GetMatchedMoviesUseCase
) : ViewModel() {

    private val sessionId =
        requireNotNull(savedStateHandle.get<String>(MatcherScreen.ARG_SESSION_ID)) {
            viewModelScope.launch {
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = "There is a problem in the session, please try to create another one",
                        duration = SnackbarDuration.Short
                    )
                )
            }
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