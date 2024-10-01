package com.moviematcher.matching.presentation.matched_list

import com.moviematcher.domain.models.Movie

sealed class MatchedResultState {
    class MatchedResults(val movies: List<Movie>) : MatchedResultState()
    data object Loading : MatchedResultState()
    data object Error : MatchedResultState()
}