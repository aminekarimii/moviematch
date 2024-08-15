package com.moviematcher.matching.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.moviematcher.matching.presentation.match.MatchingRoute

enum class MatcherScreen {
    Matcher, MoviesList
}

fun NavGraphBuilder.matcherNavigation(
    graphRoute: String,
    navHostController: NavHostController,
) {
    navigation(startDestination = MatcherScreen.Matcher.name, route = graphRoute) {
        composable(MatcherScreen.Matcher.name) {
            MatchingRoute(onMatchingComplete = {
                navHostController.navigate(MatcherScreen.MoviesList.name)
            })
        }

        /*composable(MatcherScreen.MoviesList.name) {

        }*/
    }

}