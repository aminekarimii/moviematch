package com.moviematcher.session.presentation

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.moviematcher.designsystem.R
import com.moviematcher.designsystem.component.button.PrimaryButton
import com.moviematcher.designsystem.component.button.getSocialMediaColorScheme
import com.moviematcher.designsystem.theme.MovieMatcherTheme
import com.moviematcher.designsystem.theme.dimens

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
                horizontal = MaterialTheme.dimens.screenPaddingHorizontal,
                vertical = MaterialTheme.dimens.screenPaddingVertical
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.start_or_join_session_screen_title),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.bigger))
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.88f),
            painter = painterResource(id = R.drawable.img_movies_placeholder),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Spacer(modifier = Modifier.weight(1f))
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.start_or_join_session_screen_start_session),
            onClick = onStartSession
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.large))
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            colors = getSocialMediaColorScheme(),
            onClick = onJoinSession
        ) {
            Text(text = stringResource(id = R.string.start_or_join_session_screen_join_session))
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