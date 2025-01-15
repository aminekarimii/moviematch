package com.moviematcher.matching.presentation.match

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviematcher.domain.models.SessionQuery
import com.moviematcher.domain.repositories.AuthRepository
import com.moviematcher.domain.repositories.SessionRepository
import com.moviematcher.domain.usecase.LoadMoviesBatchUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val MATCHING_TIME = 60

class MatcherViewModel(
    private val authRepository: AuthRepository,
    private val sessionRepository: SessionRepository,
    private val loadMoviesBatchUseCase: LoadMoviesBatchUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<MatcherViewState>(MatcherViewState.Loading)
    val viewState = _viewState.asStateFlow()

    private val timeLeft: MutableStateFlow<Int> = MutableStateFlow(MATCHING_TIME)
    private val isHost: MutableStateFlow<Boolean?> = MutableStateFlow(null)

    init {
        fetchRandomMovies()
        startTimer()

        viewModelScope.launch {
            sessionRepository.getSession("72FOuqFiO1d").collectLatest { session ->
                isHost.update {
                    session.isSuccess.let {
                        session.getOrNull()!!.hostId == authRepository.getCurrentUser()?.uuid
                    }
                }
            }

            sessionRepository.getMatchStatus("72FOuqFiO1d").collect { likes ->
                _viewState.update {
                    if (it is MatcherViewState.Success) {
                        it.copy(likes = likes.isSuccess.let { likes.getOrNull()!! })
                    } else {
                        it
                    }
                }
            }
        }
    }

    private fun startTimer() = viewModelScope.launch {
        while (timeLeft.value > 0) {
            delay(1000L)
            timeLeft.update { it - 1 }
            _viewState.update {
                if (it is MatcherViewState.Success) {
                    it.copy(timer = timeLeft.value)
                } else {
                    it
                }
            }
        }
        _viewState.update { MatcherViewState.MatchCompleted }
    }

    private fun fetchRandomMovies(shouldFetchNextPage: Boolean = false) = viewModelScope.launch {
        if (!shouldFetchNextPage) {
            _viewState.update { MatcherViewState.Loading }
        }

        val movies = loadMoviesBatchUseCase()

        _viewState.update {
            MatcherViewState.Success(
                matches = movies,
                timer = timeLeft.value
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
                        viewModelScope.launch {
                            if (isHost.value == true) {
                                val likedMovies = sessionRepository.getSession(
                                    "72FOuqFiO1d"
                                ).first().getOrNull()!!.hostLikes.toMutableList()
                                val uniqueMovieIds =
                                    likedMovies.addIfNotExists(currentState.matches.last().id)

                                sessionRepository.updateSession(
                                    SessionQuery(
                                        sessionId = "72FOuqFiO1d",
                                        hostLikedMovies = uniqueMovieIds,
                                    )
                                )
                            } else {
                                val likedMovies = sessionRepository.getSession(
                                    "72FOuqFiO1d"
                                ).first().getOrNull()!!.guestLikes.toMutableList()

                                val uniqueMovieIds =
                                    likedMovies.addIfNotExists(currentState.matches.last().id)

                                sessionRepository.updateSession(
                                    SessionQuery(
                                        sessionId = "72FOuqFiO1d",
                                        guestLikedMovies = uniqueMovieIds,
                                    )
                                )
                            }
                        }
                    }

                    val updatedMatches = currentState.matches.dropLast(1)
                    currentState.copy(
                        matches = updatedMatches,
                    )
                }
            } else {
                currentState
            }
        }
    }

    private fun <T> MutableList<T>.addIfNotExists(element: T): MutableList<T> {
        if (!this.contains(element)) {
            this.add(element)
        }
        return this
    }
}