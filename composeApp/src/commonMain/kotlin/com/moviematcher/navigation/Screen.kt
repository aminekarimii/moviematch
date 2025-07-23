package com.moviematcher.navigation

sealed class Screen(val route: String) {
    data object StartOrJoin : Screen("start_or_join")
    data object Tutorial : Screen("tutorial")
    data object StartSession : Screen("start_session")
    data object JoinSession : Screen("join_session")
    data object Session : Screen("session")
    data object Results : Screen("results")
}
