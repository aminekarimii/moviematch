package com.moviematcher.matching.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.moviematcher.matching.presentation.match.MatchingRoute
import com.moviematcher.matching.presentation.matched_list.MatchedResultListRoute
import com.moviematcher.matching.presentation.matched_list.NoMatchFoundRoute

enum class MatcherScreen {
    Matcher, MoviesList, NoMatchFound
}

fun NavGraphBuilder.matcherNavigation(
    graphRoute: String,
    navHostController: NavHostController,
) {
    navigation(startDestination = MatcherScreen.NoMatchFound.name, route = graphRoute) {
        composable(MatcherScreen.Matcher.name) {
            MatchingRoute(onMatchingComplete = {
                navHostController.navigate(MatcherScreen.MoviesList.name)
            })
        }

        composable(MatcherScreen.MoviesList.name) {
            MatchedResultListRoute()
        }

        composable(MatcherScreen.NoMatchFound.name) {
            NoMatchFoundRoute()
        }
    }

}