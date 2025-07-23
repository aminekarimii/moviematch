package com.moviematcher

import androidx.compose.runtime.*
import com.moviematcher.navigation.Screen
import com.moviematcher.screens.JoinSessionScreen
import com.moviematcher.screens.ResultsScreen
import com.moviematcher.screens.SessionScreen
import com.moviematcher.screens.StartOrJoinSessionScreen
import com.moviematcher.screens.StartSessionScreen
import com.moviematcher.screens.tutorial.TutorialScreen
import com.moviematcher.theme.MovieMatcherTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun MovieMatchApp() {
    MovieMatcherTheme {
        var currentScreen by remember { mutableStateOf<Screen>(Screen.StartOrJoin) }

        when (currentScreen) {

            Screen.StartOrJoin -> {
                StartOrJoinSessionScreen(
                    onStartSession = {
                        currentScreen = Screen.Tutorial
                    },
                    onJoinSession = {
                        currentScreen = Screen.JoinSession
                    }
                )
            }

            Screen.Tutorial -> {
                TutorialScreen(
                    onStartSession = {
                        currentScreen = Screen.StartSession
                    }
                )
            }

            Screen.StartSession -> {
                StartSessionScreen(
                    onStartSession = {
                        currentScreen = Screen.Session
                    },
                    onBackToHome = {
                        currentScreen = Screen.StartOrJoin
                    }
                )
            }

            Screen.JoinSession -> {
                JoinSessionScreen(
                    onJoinSession = {
                        currentScreen = Screen.Session
                    },
                    onBackToHome = {
                        currentScreen = Screen.StartOrJoin
                    }
                )
            }

            Screen.Session -> {
                SessionScreen(
                    onBackToHome = {
                        currentScreen = Screen.StartOrJoin
                    },
                    onShowResults = {
                        currentScreen = Screen.Results
                    }
                )
            }

            Screen.Results -> {
                ResultsScreen(
                    onBackToHome = {
                        currentScreen = Screen.StartOrJoin
                    },
                    onNewSession = {
                        currentScreen = Screen.Session
                    }
                )
            }
        }
    }
}