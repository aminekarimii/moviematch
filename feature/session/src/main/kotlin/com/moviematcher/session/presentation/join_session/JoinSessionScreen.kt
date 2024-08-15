package com.moviematcher.session.presentation.join_session

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.moviematcher.designsystem.R
import com.moviematcher.designsystem.component.button.PrimaryButton
import com.moviematcher.designsystem.theme.MovieMatcherTheme
import com.moviematcher.designsystem.theme.Red70Transparent
import com.moviematcher.designsystem.theme.dimens

@Composable
fun JoinSessionScreen(
    onJoinSession: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var sessionCode by remember { mutableStateOf("") }
    Box {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .scale(1.5f)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Red70Transparent, Color(0x00000000)),
                    ),
                )
        ) {
            // Empty body
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.join_session_screen_title),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.big))
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                painter = painterResource(id = com.moviematcher.session.R.drawable.img_app_preview),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.big))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.dimens.screenPaddingHorizontal)
                    .focusRequester(focusRequester),
                value = sessionCode,
                onValueChange = {
                    sessionCode = it
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    disabledContainerColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                ),
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium))
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.dimens.screenPaddingHorizontal),
                text = stringResource(id = R.string.start_or_join_session_screen_join_session),
                enabled = sessionCode.isNotBlank()
            ) {
                onJoinSession(sessionCode)
            }
            Image(
                painter = painterResource(id = com.moviematcher.session.R.drawable.img_waves),
                contentDescription = null
            )
            SideEffect {
                focusRequester.requestFocus()
            }
        }
    }
}

@Preview
@Composable
private fun JoinSessionScreenPreview() {
    MovieMatcherTheme {
        Surface {
            JoinSessionScreen({})
        }
    }
}
