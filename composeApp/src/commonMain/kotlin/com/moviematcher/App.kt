package com.moviematcher

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.*
import com.moviematcher.navigation.Screen
import com.moviematcher.screens.*
import com.moviematcher.theme.MovieMatcherTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(padding: PaddingValues? = null) {
    MovieMatcherTheme {
        var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }

        when (currentScreen) {
            Screen.Home -> {
                HomeScreen(
                    onCreateSession = {
                        currentScreen = Screen.StartOrJoin
                    },
                    onJoinSession = {
                        currentScreen = Screen.StartOrJoin
                    }
                )
            }

            Screen.StartOrJoin -> {
                StartOrJoinSessionScreen(
                    onStartSession = {
                        currentScreen = Screen.StartSession
                    },
                    onJoinSession = {
                        currentScreen = Screen.JoinSession
                    }
                )
            }

            Screen.StartSession -> {
                StartSessionScreen(
                    onStartSession = {
                        currentScreen = Screen.Session
                    },
                    onBackToHome = {
                        currentScreen = Screen.Home
                    }
                )
            }

            Screen.JoinSession -> {
                JoinSessionScreen(
                    onJoinSession = {
                        currentScreen = Screen.Session
                    },
                    onBackToHome = {
                        currentScreen = Screen.Home
                    }
                )
            }

            Screen.Session -> {
                SessionScreen(
                    onBackToHome = {
                        currentScreen = Screen.Home
                    },
                    onShowResults = {
                        currentScreen = Screen.Results
                    }
                )
            }

            Screen.Results -> {
                ResultsScreen(
                    onBackToHome = {
                        currentScreen = Screen.Home
                    },
                    onNewSession = {
                        currentScreen = Screen.Session
                    }
                )
            }
        }
    }
}