package com.moviematcher.session.presentation.join_session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviematcher.domain.usecase.JoinSessionResult
import com.moviematcher.domain.usecase.JoinSessionUseCase
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
    private val authRepository: com.moviematcher.domain.repositories.AuthRepository,
    private val sessionRepository: com.moviematcher.domain.repositories.SessionRepository,
    private val joinSessionUseCase: JoinSessionUseCase,
) : ViewModel() {

    private var _uiState: MutableStateFlow<JoinSessionUiState> =
        MutableStateFlow(JoinSessionUiState.Idle)
    val uiState: StateFlow<JoinSessionUiState> = _uiState

    fun onJoinSession(sessionCode: String) {
        viewModelScope.launch {
            _uiState.update { JoinSessionUiState.Loading }

            when (joinSessionUseCase(sessionCode)) {
                is JoinSessionResult.Success -> {
                    _uiState.update { JoinSessionUiState.StartMatch }
                }

                is JoinSessionResult.SessionNotFound -> {
                    _uiState.update { JoinSessionUiState.Error("Session not found") }
                }

                is JoinSessionResult.SessionCompleted -> {
                    _uiState.update { JoinSessionUiState.Error("Session is completed") }
                }

                is JoinSessionResult.SessionActive -> {
                    _uiState.update { JoinSessionUiState.Error("The session has started already, you can't join right now !") }
                }

                is JoinSessionResult.ServerError -> {
                    _uiState.update { JoinSessionUiState.Error("An unexpected error occurred. Please try again.") }
                }

            }
        }
    }

}