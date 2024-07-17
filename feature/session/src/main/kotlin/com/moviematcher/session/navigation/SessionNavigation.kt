package com.moviematcher.session.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.moviematcher.session.presentation.start_or_join.StartOrJoinSessionScreen
import com.moviematcher.session.presentation.start_session.StartSessionScreen
import com.moviematcher.session.presentation.tutorial.TutorialScreen

enum class SessionScreen {
    START_OR_JOIN_SESSION, TUTORIAL, START_SESSION
}

fun NavGraphBuilder.sessionNavigation(
    graphRoute: String,
    navHostController: NavHostController
) {
    navigation(startDestination = SessionScreen.START_OR_JOIN_SESSION.name, route = graphRoute) {
        composable(SessionScreen.START_OR_JOIN_SESSION.name) {
            StartOrJoinSessionScreen(
                onStartSession = {
                    navHostController.navigate(SessionScreen.TUTORIAL.name)
                },
                onJoinSession = {}
            )
        }
        composable(SessionScreen.TUTORIAL.name) {
            TutorialScreen(
                onStartSession = {
                    navHostController.navigate(SessionScreen.START_SESSION.name)
                }
            )
        }
        composable(SessionScreen.START_SESSION.name) {
            StartSessionScreen()
        }
    }
}