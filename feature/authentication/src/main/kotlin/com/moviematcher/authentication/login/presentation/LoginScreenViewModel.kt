package com.moviematcher.authentication.login.presentation

import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.api.ApiException
import com.moviematcher.designsystem.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val authRepository: com.moviematcher.domain.repositories.AuthRepository
) : ViewModel() {
    private val _viewState = MutableStateFlow(LoginScreenViewState())
    val viewState = _viewState.asStateFlow()

    fun signIn(
        signInMethod: SignInMethod,
        activityResult: ActivityResult? = null
    ) {
        viewModelScope.launch {
            when (signInMethod) {
                SignInMethod.GUEST -> {
                    _viewState.value = _viewState.value.copy(isLoading = true, errorMsg = null)
                    try {
                        authRepository.loginAsGuest()
                        _viewState.value = _viewState.value.copy(isLoading = false, loggedIn = true)
                    } catch (e: Exception) {
                        _viewState.value = _viewState.value.copy(
                            isLoading = false,
                            errorMsg = R.string.sign_in_screen_error,
                            loggedIn = false
                        )
                    }
                }
                SignInMethod.GOOGLE -> handleGoogleSignInResult(activityResult!!)
            }
        }
    }

    private suspend fun handleGoogleSignInResult(activityResult: ActivityResult) {
        try {
            _viewState.update { it.copy(isLoading = true) }
            val task = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data).result
            val account = com.moviematcher.domain.models.Account(task.idToken!!)
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