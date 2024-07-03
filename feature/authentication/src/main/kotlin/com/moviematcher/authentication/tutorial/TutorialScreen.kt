package com.moviematcher.authentication.tutorial

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension.Companion.fillToConstraints
import com.moviematcher.designsystem.R
import com.moviematcher.designsystem.component.button.PrimaryButton
import com.moviematcher.designsystem.theme.MovieMatcherTheme
import com.moviematcher.designsystem.theme.Red50
import com.moviematcher.designsystem.theme.dimens

@Composable
fun TutorialScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.dimens.default),
        ) {
            val (title, steps, button) = createRefs()

            TutorialTitle(modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
            Steps(modifier = Modifier.constrainAs(steps) {
                top.linkTo(title.bottom)
                bottom.linkTo(button.top)
                height = fillToConstraints
            })
            PrimaryButton(
                modifier = Modifier
                    .constrainAs(button) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .fillMaxWidth(),
                text = "Start a session"
            ) {

            }
        }
    }
}


@Composable
fun TutorialTitle(
    modifier: Modifier = Modifier,
) {
    val normal = SpanStyle(MaterialTheme.colorScheme.onPrimary)
    val redSpan = SpanStyle(color = Red50)
    val part2 =
        stringResource(id = R.string.tutorial_screen_title_part2)

    val footer = buildAnnotatedString {
        withStyle(normal) {
            append(stringResource(id = R.string.tutorial_screen_title_part1))
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
    )
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    showSystemUi = true
)
@Composable
private fun TutorialScreenPreview() {
    MovieMatcherTheme {
        TutorialScreen()
    }
}