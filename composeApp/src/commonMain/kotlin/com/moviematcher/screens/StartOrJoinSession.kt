package com.moviematcher.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import dev.icerock.moko.resources.compose.stringResource
import dev.icerock.moko.resources.compose.painterResource
import com.moviematcher.resources.MR
import com.moviematcher.theme.MovieMatcherTheme

@Composable
fun StartOrJoinSessionScreen(
    onStartSession: () -> Unit,
    onJoinSession: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(
                horizontal = 32.dp,
                vertical = 24.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(MR.strings.start_or_join_session_screen_title),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(48.dp))
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.88f),
            painter = painterResource(MR.images.img_movies_placeholder),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Spacer(modifier = Modifier.weight(1f))
        PrimaryButton (
            modifier = Modifier.fillMaxWidth(),
            onClick = onStartSession
        ) {
            Text(text = stringResource(MR.strings.start_or_join_session_screen_start_session))
        }
        Spacer(modifier = Modifier.height(16.dp))
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            colors = getSocialMediaColorScheme(),
            onClick = onJoinSession,
        ) {
            Text(text = stringResource(MR.strings.start_or_join_session_screen_join_session))
        }
    }
}



@Preview
@Composable
private fun StartOrJoinSessionRoutePreview() {
    MovieMatcherTheme {
        Surface {
            StartOrJoinSessionScreen(onStartSession = {}, onJoinSession = {})
        }
    }
}