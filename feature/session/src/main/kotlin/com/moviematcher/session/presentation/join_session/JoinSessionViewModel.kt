package com.moviematcher.session.presentation.join_session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moviematcher.domain.repositories.SessionRepository
import kotlinx.coroutines.launch

class JoinSessionViewModel constructor(
    private val sessionRepository: SessionRepository,
) : ViewModel() {

    fun onJoinSession(sessionCode: String) {
        viewModelScope.launch {
            sessionRepository.updateSession(
                sessionId = sessionCode,
                isGuestReady = true
            )
        }
    }
}