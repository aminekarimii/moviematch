package com.moviematcher.matching.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.moviematcher.matching.presentation.match.MatchingRoute
import com.moviematcher.matching.presentation.matched_list.MatchedResultListRoute

enum class MatcherScreen(val route: String) {
    MATCHER("matcher"), MOVIES_LIST("movies_list/{arg_session_id}");

    companion object {
        const val ARG_SESSION_ID = "arg_session_id"
        const val GRAPH_ROUTE = "matching/{$ARG_SESSION_ID}"
    }
}

fun NavGraphBuilder.matcherNavigation(
    graphRoute: String,
    navHostController: NavHostController,
) {
    navigation(
        startDestination = MatcherScreen.MATCHER.route,
        route = graphRoute,
        arguments = listOf(
            navArgument(MatcherScreen.ARG_SESSION_ID) {
                type = NavType.StringType
            }
        )
    ) {
        composable(route = MatcherScreen.MATCHER.route) {
            MatchingRoute(
                onMatchingComplete = {
                    val route =
                        MatcherScreen.MOVIES_LIST.route.replace(
                            "{${MatcherScreen.ARG_SESSION_ID}}",
                            it
                        )
                    navHostController.navigate(
                        route = route,
                        navOptions = navOptions {
                            popUpTo(route = graphRoute) {
                                inclusive = false
                            }
                        }
                    )
                }
            )
        }

        composable(
            route = MatcherScreen.MOVIES_LIST.route,
            arguments = listOf(
                navArgument(MatcherScreen.ARG_SESSION_ID) {
                    type = NavType.StringType
                }
            )
        ) {
            MatchedResultListRoute()
        }
    }
}