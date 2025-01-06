package com.moviematcher.session.util

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Parcelable
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import kotlinx.parcelize.Parcelize

@Parcelize
data class NFCSession(
    val sessionId: String,
) : Parcelable

@SuppressLint("UnspecifiedRegisterReceiverFlag")
@Composable
fun NfcBroadcastReceiver(
    onSuccess: (String) -> Unit,
) {
    val context = LocalContext.current

    val currentOnSystemEvent by rememberUpdatedState(onSuccess)

    DisposableEffect(context) {
        val intentFilter = IntentFilter(INTENT_ACTION_NFC_READ)
        val broadcast = object : BroadcastReceiver() {
            @RequiresApi(Build.VERSION_CODES.TIRAMISU)
            override fun onReceive(
                context: Context?,
                intent: Intent?,
            ) {
                intent?.getParcelableCompatibility("NFC_SESSION", NFCSession::class.java)
                    .let { nfcSession ->
                        nfcSession?.let {
                            Log.d(
                                "TAG",
                                "onReceive: this is the session id received from the user ! $it"
                            )
                            currentOnSystemEvent(it.sessionId)
                        }
                    }
            }
        }

        ContextCompat.registerReceiver(
            context, broadcast, intentFilter, ContextCompat.RECEIVER_NOT_EXPORTED
        )

        onDispose {
            context.unregisterReceiver(broadcast)
        }
    }
}

const val INTENT_ACTION_NFC_READ = "com.moviematcher.session.util.INTENT_ACTION_NFC_READ"
