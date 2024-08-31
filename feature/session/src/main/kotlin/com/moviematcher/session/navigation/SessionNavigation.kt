package com.moviematcher.session.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.moviematcher.session.presentation.join_session.JoinSessionScreen
import com.moviematcher.session.presentation.start_or_join.StartOrJoinSessionScreen
import com.moviematcher.session.presentation.start_session.StartSessionScreen
import com.moviematcher.session.presentation.tutorial.TutorialScreen

enum class SessionScreen {
    START_OR_JOIN_SESSION, TUTORIAL, START_SESSION, JOIN_SESSION
}

fun NavGraphBuilder.sessionNavigation(
    graphRoute: String,
    navHostController: NavHostController,
    onJoinSession: (String) -> Unit
) {
    navigation(startDestination = SessionScreen.START_OR_JOIN_SESSION.name, route = graphRoute) {
        composable(SessionScreen.START_OR_JOIN_SESSION.name) {
            StartOrJoinSessionScreen(
                onStartSession = {
                    navHostController.navigate(SessionScreen.TUTORIAL.name)
                },
                onJoinSession = {
                    navHostController.navigate(SessionScreen.JOIN_SESSION.name)
                }
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
            StartSessionScreen(onJoinSession = onJoinSession)
        }
        composable(SessionScreen.JOIN_SESSION.name) {
            JoinSessionScreen(onJoinSession = onJoinSession)
        }
    }
}