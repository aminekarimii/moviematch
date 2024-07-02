package com.moviematcher.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.moviematcher.app.navigation.MMNavHost
import com.moviematcher.app.navigation.StartDestinationUseCase
import com.moviematcher.designsystem.theme.MovieMatcherTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class MainActivity : ComponentActivity() {

    val startDestinationUseCase by inject<StartDestinationUseCase>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        loadActivityModule()

        var startDestination by mutableStateOf<String?>(null)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                startDestination = startDestinationUseCase()
            }
        }
        splashScreen.setKeepOnScreenCondition {
            startDestination == null
        }

        setContent {
            val startDestinationValue = startDestination ?: return@setContent
            val navController = rememberNavController()

            MovieMatcherApp(
                navController = navController,
                startDestination = startDestinationValue
            )
        }
    }

    private fun loadActivityModule() {
        val activityModule = module {
            single<GoogleSignInClient> {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestId()
                    .build()
                GoogleSignIn.getClient(this@MainActivity, gso)
            }
        }
        loadKoinModules(activityModule)
    }
}

@Composable
fun MovieMatcherApp(
    navController: NavHostController,
    startDestination: String
) {
    MovieMatcherTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
        ) {
            MMNavHost(navController = navController, startDestination = startDestination)
        }
    }
}