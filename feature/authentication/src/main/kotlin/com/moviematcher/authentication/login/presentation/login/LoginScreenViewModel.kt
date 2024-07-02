package com.moviematcher.authentication.login.presentation.login

import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.api.ApiException
import com.moviematcher.authentication.login.domain.models.Account
import com.moviematcher.authentication.login.domain.repositories.AuthRepository
import com.moviematcher.designsystem.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginScreenViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _viewState = MutableStateFlow(LoginScreenViewState())
    val viewState = _viewState.asStateFlow()

    fun signIn(activityResult: ActivityResult) {
        viewModelScope.launch {
            try {
                _viewState.update { it.copy(isLoading = true) }
                val task = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
                val account = task.await().let { Account(it.email!!, it.idToken!!) }
                authRepository.login(account)
                _viewState.update { it.copy(isLoading = false, errorMsg = null, loggedIn = true) }
            } catch (e: ApiException) {
                if (e.statusCode == GoogleSignInStatusCodes.SIGN_IN_FAILED) {
                    _viewState.update {
                        it.copy(
                            isLoading = false,
                            errorMsg = R.string.sign_in_screen_error,
                            loggedIn = false
                        )
                    }
                } else {
                    _viewState.update {
                        it.copy(
                            isLoading = false,
                            errorMsg = null,
                            loggedIn = false
                        )
                    }
                }
            } catch (e: Exception) {
                _viewState.update {
                    it.copy(
                        isLoading = false,
                        errorMsg = R.string.sign_in_screen_error,
                        loggedIn = false
                    )
                }
            }
        }
    }
}