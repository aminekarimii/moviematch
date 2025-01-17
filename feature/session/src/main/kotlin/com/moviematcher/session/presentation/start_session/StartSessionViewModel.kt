package com.moviematcher.session.presentation.start_session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviematcher.domain.repositories.AuthRepository
import com.moviematcher.domain.repositories.SessionRepository
import com.moviematcher.domain.usecase.LoadMoviesBatchUseCase
import com.moviematcher.session.util.RandomUtil.generateUniqueId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StartSessionViewModel(
    private val authRepository: AuthRepository,
    private val sessionRepository: SessionRepository,
) : ViewModel() {

    val sessionCode = generateUniqueId()

    private val guestUi = MutableStateFlow(false)
    val guestUiState = guestUi.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null,
    )

    fun isGuestReady(sessionId: String) {
        viewModelScope.launch {
            sessionRepository.isGuestReady(sessionId)
                .collectLatest {
                    guestUi.value = it.getOrNull() ?: false
                }
        }
    }

    fun createNewSession(sessionId: String) {
        viewModelScope.launch {
            sessionRepository.createNewSession(
                sessionId = sessionId,
                hostId = authRepository.getCurrentUser()?.uuid
            )
        }
    }
}