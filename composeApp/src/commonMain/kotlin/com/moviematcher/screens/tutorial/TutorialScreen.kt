package com.moviematcher.screens.tutorial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import org.jetbrains.compose.ui.tooling.preview.Preview
import dev.icerock.moko.resources.compose.stringResource
import com.moviematcher.resources.MR
import com.moviematcher.screens.PrimaryButton
import com.moviematcher.theme.MovieMatcherTheme
import com.moviematcher.theme.Red50
import com.moviematcher.theme.dimens

@Composable
fun TutorialScreen(
    onStartSession: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = MaterialTheme.dimens.screenPaddingHorizontal,
                vertical = MaterialTheme.dimens.screenPaddingVertical
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TutorialTitle()

        Steps(
            modifier = Modifier.weight(1f)
        )

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Start a session",
            onClick = onStartSession
        )
    }
}


@Composable
fun TutorialTitle(
    modifier: Modifier = Modifier,
) {
    val normal = SpanStyle(MaterialTheme.colorScheme.onPrimary)
    val redSpan = SpanStyle(color = Red50)
    val part2 = stringResource(MR.strings.tutorial_screen_title_part2)

    val footer = buildAnnotatedString {
        withStyle(normal) {
            append(stringResource(MR.strings.tutorial_screen_title_part1))
            append(" ")
        }
        withStyle(redSpan) {
            append(part2)
        }
    }
    Text(
        modifier = modifier,
        text = footer,
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
private fun TutorialScreenPreview() {
    MovieMatcherTheme {
        Surface {
            TutorialScreen {}
        }
    }
}