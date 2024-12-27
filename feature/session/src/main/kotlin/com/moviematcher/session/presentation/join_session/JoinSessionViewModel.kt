package com.moviematcher.session.presentation.join_session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviematcher.domain.models.SessionQuery
import com.moviematcher.domain.repositories.AuthRepository
import com.moviematcher.domain.repositories.SessionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.sql.Timestamp

sealed class JoinSessionUiState {
    data object Idle : JoinSessionUiState()
    data object Loading : JoinSessionUiState()
    data object StartMatch : JoinSessionUiState()
    data class Error(val message: String) : JoinSessionUiState()
}

class JoinSessionViewModel(
    private val authRepository: AuthRepository,
    private val sessionRepository: SessionRepository,
) : ViewModel() {

    private var _uiState: MutableStateFlow<JoinSessionUiState> =
        MutableStateFlow(JoinSessionUiState.Idle)
    val uiState: StateFlow<JoinSessionUiState> = _uiState

    fun onJoinSession(sessionCode: String) {
        viewModelScope.launch {
            _uiState.update { JoinSessionUiState.Loading }

            val result = sessionRepository.getSession(sessionCode).first()
            val newState = if (result.isSuccess) {
                sessionRepository.updateSession(
                    SessionQuery(
                        updatedAt = Timestamp(System.currentTimeMillis()),
                        sessionId = sessionCode,
                        guestId = authRepository.getCurrentUser()?.uuid
                    )
                )
                JoinSessionUiState.StartMatch
            } else {
                JoinSessionUiState.Error("Session not found")
            }

            _uiState.update { newState }
        }
    }
}