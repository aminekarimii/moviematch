package com.moviematcher.session.presentation.join_session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviematcher.domain.repositories.SessionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class JoinSessionUiState {
    data object Idle : JoinSessionUiState()
    data object Loading : JoinSessionUiState()
    data object StartMatch : JoinSessionUiState()
    data class Error(val message: String) : JoinSessionUiState()
}

class JoinSessionViewModel(
    private val sessionRepository: SessionRepository,
) : ViewModel() {

    private var _uiState: MutableStateFlow<JoinSessionUiState> =
        MutableStateFlow(JoinSessionUiState.Idle)
    val uiState: StateFlow<JoinSessionUiState> = _uiState

    fun onJoinSession(sessionCode: String) {
        viewModelScope.launch {
            _uiState.update { JoinSessionUiState.Loading }

            sessionRepository.getSession(sessionCode).collect { result ->
                val newState = if (result.isSuccess) {
                    sessionRepository.updateSession(
                        sessionId = sessionCode,
                        isGuestReady = true
                    )
                    JoinSessionUiState.StartMatch
                } else {
                    JoinSessionUiState.Error("Session not found")
                }

                _uiState.update { newState }
            }
        }
    }
}