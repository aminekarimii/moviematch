package com.moviematcher.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.moviematcher.resources.MR
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun StartSessionScreen(
    onStartSession: () -> Unit,
    onBackToHome: () -> Unit
) {
    var showQRCode by remember { mutableStateOf(false) }
    val sessionCode = remember { (1000..9999).random().toString() }

    Box(modifier = Modifier.padding(bottom = 24.dp)) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Session Created",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            Image(
                modifier = Modifier
                    .fillMaxWidth(),
                painter = painterResource(MR.images.img_app_preview),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(32.dp))
            Footer(sessionCode = sessionCode)
            Spacer(modifier = Modifier.height(96.dp))
        }

        PrimaryButton(
            text = "Show QR Code",
            onClick = { showQRCode = !showQRCode },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
        )
    }
}


@Composable
private fun Footer(sessionCode: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier
                .weight(0.8f),
            value = sessionCode,
            onValueChange = {},
            enabled = false,
            readOnly = true,
            colors = TextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledIndicatorColor = Color.Transparent
            ),
        )

        ClickableIcon(
            onClick = { /* TODO: Share functionality */ },
            modifier = Modifier
                .weight(0.2f)
                .aspectRatio(1f)
        ) {
            Text("Share")
        }
    }
}
