package com.moviematcher.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moviematcher.authentication.login.presentation.LoginRoute
import com.moviematcher.matching.navigation.MatcherScreen
import com.moviematcher.matching.navigation.matcherNavigation
import com.moviematcher.session.navigation.sessionNavigation

@Composable
fun MMNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String
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
            onJoinSession = {
                val route = MatcherScreen.GRAPH_ROUTE.replace(MatcherScreen.ARG_SESSION_ID, it)
                navController.navigate(route)
            }
        )
        matcherNavigation(
            graphRoute = MatcherScreen.GRAPH_ROUTE,
            navHostController = navController,
        )
    }
}