package com.moviematcher.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.moviematcher.authentication.login.presentation.LoginRoute
import com.moviematcher.matching.navigation.MatcherScreen
import com.moviematcher.matching.navigation.matcherNavigation
import com.moviematcher.session.ObserveAsEvents
import com.moviematcher.session.SnackbarController
import com.moviematcher.session.navigation.SessionScreen
import com.moviematcher.session.navigation.sessionNavigation
import kotlinx.coroutines.launch

@Composable
fun MMNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()
    ObserveAsEvents(
        flow = SnackbarController.events,
        snackbarHostState
    ) { event ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()

            val result = snackbarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.name,
                duration = event.duration ?: SnackbarDuration.Long
            )

            if (result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            modifier = modifier.padding(innerPadding),
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
                    val route =
                        MatcherScreen.GRAPH_ROUTE.replace("{${MatcherScreen.ARG_SESSION_ID}}", it)
                    navController.navigate(
                        route = route,
                        navOptions = navOptions {
                            popUpTo(route = SessionScreen.START_OR_JOIN_SESSION.name) {
                                inclusive = false
                            }
                        }
                    )
                }
            )
            matcherNavigation(
                graphRoute = MatcherScreen.GRAPH_ROUTE,
                navHostController = navController,
            )
        }
    }
}