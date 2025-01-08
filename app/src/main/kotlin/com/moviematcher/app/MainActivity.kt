package com.moviematcher.app

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Build
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
import com.moviematcher.session.util.INTENT_ACTION_NFC_READ
import com.moviematcher.session.util.NFCSession
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class MainActivity : ComponentActivity() {

    val startDestinationUseCase by inject<StartDestinationUseCase>()
    private var nfcAdapter: NfcAdapter? = null
    private var nfcSession: NFCSession? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
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
                startDestination = startDestinationValue,
                onUpdateNFCSession = { updatedNfcSession ->
                    nfcSession = updatedNfcSession
                }
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

    private fun enableNfcForegroundDispatch() {
        nfcAdapter?.let { adapter ->
            if (adapter.isEnabled) {
                val nfcIntentFilter = arrayOf(
                    IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED),
                    IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED),
                    IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)
                )

                val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    PendingIntent.getActivity(
                        this,
                        0,
                        Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                        PendingIntent.FLAG_MUTABLE
                    )
                } else {
                    PendingIntent.getActivity(
                        this,
                        0,
                        Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                }
                adapter.enableForegroundDispatch(
                    this, pendingIntent, nfcIntentFilter, null
                )
            }
        }
    }

    private fun disableNfcForegroundDispatch() {
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onResume() {
        super.onResume()
        enableNfcForegroundDispatch()
    }

    override fun onPause() {
        super.onPause()
        disableNfcForegroundDispatch()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.also {
            sendBroadcast(Intent(INTENT_ACTION_NFC_READ).apply {
                putExtra(
                    NfcAdapter.EXTRA_TAG,
                    it.getParcelableCompatibility(NfcAdapter.EXTRA_TAG, Tag::class.java)
                )
                putExtra("NFC_SESSION", nfcSession)
                setPackage(packageName)
            })
        }
    }
}

@Composable
fun MovieMatcherApp(
    navController: NavHostController,
    startDestination: String,
    onUpdateNFCSession: (NFCSession) -> Unit,
) {
    MovieMatcherTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
        ) {
            MMNavHost(
                navController = navController,
                startDestination = startDestination,
                onUpdateNFCSession = onUpdateNFCSession
            )
        }
    }
}