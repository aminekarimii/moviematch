package com.moviematcher.matching.presentation.matched_list

sealed class MatchedResultState {
    class MatchedResults(val movies: List<MovieModel>) : MatchedResultState()
    data object Loading : MatchedResultState()
    data object Error : MatchedResultState()
}