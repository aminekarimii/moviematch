package com.moviematcher.authentication.login.presentation

data class LoginScreenViewState(
    val isLoading: Boolean = false,
    val loggedIn: Boolean = false,
    val errorMsg: Int? = null
)