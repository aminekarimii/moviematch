package com.moviematcher.matching.presentation.match

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviematcher.domain.models.Movie
import com.moviematcher.domain.models.SessionQuery
import com.moviematcher.domain.repositories.AuthRepository
import com.moviematcher.domain.repositories.SessionRepository
import com.moviematcher.domain.usecase.LoadMoviesBatchUseCase
import com.moviematcher.matching.navigation.MatcherScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val MATCHING_TIME = 60

class MatcherViewModel(
    savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository,
    private val sessionRepository: SessionRepository,
    private val loadMoviesBatchUseCase: LoadMoviesBatchUseCase
) : ViewModel() {

    private val sessionId =
        requireNotNull(savedStateHandle.get<String>(MatcherScreen.ARG_SESSION_ID)) {
            "The ARG_SESSION_ID should be passed in navigation !!"
        }

    private val _viewState = MutableStateFlow<MatcherViewState>(MatcherViewState.Loading)
    val viewState = _viewState.asStateFlow()

    private val timeLeft: MutableStateFlow<Int> = MutableStateFlow(MATCHING_TIME)
    private val isHost: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    private val allMovies: MutableList<Movie> = mutableListOf()
    private var currentBatchIndex = 0

    init {
        fetchRandomMovies()

        startTimer()

        viewModelScope.launch {
            sessionRepository.getSession(sessionId).collectLatest { session ->
                isHost.update {
                    session.isSuccess.let {
                        session.getOrNull()?.hostId == authRepository.getCurrentUser()?.uuid
                    }
                }
            }

            sessionRepository.getMatchStatus(sessionId).collect { likes ->
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
        _viewState.update { MatcherViewState.MatchCompleted(sessionId) }
    }

    private fun fetchRandomMovies(shouldFetchNextPage: Boolean = false) = viewModelScope.launch {
        if (!shouldFetchNextPage) {
            _viewState.update { MatcherViewState.Loading }
        }

        val movies = loadMoviesBatchUseCase()
        allMovies.addAll(movies)
        updateViewStateWithNextBatch()
    }

    fun swipeLatestMovie(updateCounter: Boolean) {
        _viewState.update { currentState ->
            if (currentState is MatcherViewState.Success) {
                if (updateCounter) {
                    viewModelScope.launch {
                        updateSessionWithLikedMovie(
                            sessionId = sessionId,
                            movieId = currentState.matches.last().id,
                            isHost = isHost.value == true
                        )
                    }
                }

                val updatedMatches = currentState.matches.dropLast(1)

                // Load next batch if there's only 1 item left
                if (updatedMatches.size <= 1) {
                    loadNextBatch()
                }

                return@update currentState.copy(matches = updatedMatches)
            }

            currentState
        }
    }

    private fun loadNextBatch() {
        viewModelScope.launch {
            if (currentBatchIndex < allMovies.size) {
                updateViewStateWithNextBatch()
            } else {
                fetchRandomMovies(shouldFetchNextPage = true)
            }
        }
    }

    private fun updateViewStateWithNextBatch() {
        val nextBatch = allMovies.subList(
            currentBatchIndex,
            (currentBatchIndex + 15).coerceAtMost(allMovies.size)
        )

        currentBatchIndex += nextBatch.size

        _viewState.update { currentState ->
            when (currentState) {
                is MatcherViewState.Success -> {
                    currentState.copy(
                        matches = nextBatch + currentState.matches
                    )
                }
                else -> {
                    MatcherViewState.Success(
                        matches = nextBatch,
                        timer = timeLeft.value
                    )
                }
            }
        }
    }

    private suspend fun updateSessionWithLikedMovie(
        sessionId: String,
        movieId: Int,
        isHost: Boolean
    ) {
        val likedMovies = sessionRepository.getSession(sessionId)
            .first()
            .getOrNull()
            ?.let { session ->
                if (isHost) session.hostLikes.toMutableList()
                else session.guestLikes.toMutableList()
            } ?: mutableListOf()

        val uniqueMovieIds = likedMovies.addIfNotExists(movieId)

        sessionRepository.updateSession(
            SessionQuery(
                sessionId = sessionId,
                hostLikedMovies = if (isHost) uniqueMovieIds else null,
                guestLikedMovies = if (!isHost) uniqueMovieIds else null,
            )
        )
    }

    private fun <T> MutableList<T>.addIfNotExists(element: T): MutableList<T> {
        if (!this.contains(element)) {
            this.add(element)
        }
        return this
    }
}
