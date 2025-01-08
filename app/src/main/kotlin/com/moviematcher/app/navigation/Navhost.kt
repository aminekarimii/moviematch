package com.moviematcher.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moviematcher.authentication.login.presentation.LoginRoute
import com.moviematcher.matching.navigation.matcherNavigation
import com.moviematcher.session.navigation.sessionNavigation
import com.moviematcher.session.util.NFCSession

@Composable
fun MMNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String,
    onUpdateNFCSession: (NFCSession) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.AUTH.name) {
            LoginRoute(
                onUserLoggedIn = {
                    navController.navigate(Screen.SESSION.name)
                },
            )
        }
        sessionNavigation(
            graphRoute = Screen.SESSION.name,
            navHostController = navController,
            onUpdateNFCSession = onUpdateNFCSession,
            onJoinSession = {
                navController.navigate(Screen.MATCHING.name)
            }
        )
        matcherNavigation(
            graphRoute = Screen.MATCHING.name,
            navHostController = navController,
        )
    }
}