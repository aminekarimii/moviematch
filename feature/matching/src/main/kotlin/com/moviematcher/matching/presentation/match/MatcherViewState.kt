package com.moviematcher.matching.presentation.match

import androidx.compose.runtime.Immutable
import com.moviematcher.domain.models.Movie

@Immutable
sealed class MatcherViewState {
    data object Loading : MatcherViewState()
    data object MatchCompleted : MatcherViewState()
    data class Error(val message: String) : MatcherViewState()

    data class Success(
        val likes: Int = 0,
        val timer: Int,
        val matches: List<Movie>,
    ) : MatcherViewState()
}