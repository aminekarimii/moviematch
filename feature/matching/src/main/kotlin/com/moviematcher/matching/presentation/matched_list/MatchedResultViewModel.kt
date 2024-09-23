package com.moviematcher.matching.presentation.matched_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviematcher.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class MatchedResultViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _viewState = MutableSharedFlow<MatchedResultState>()
    val viewState: SharedFlow<MatchedResultState> = _viewState

    init {
        viewModelScope.launch {
            _viewState.emit(MatchedResultState.Loading)
            val movies = movieRepository.getMovies()
            _viewState.emit(
                MatchedResultState.MatchedResults(
                    movies = movies.map { movie ->
                        movie.copy(index = movie.id)
                    }
                )
            )
        }
    }
}