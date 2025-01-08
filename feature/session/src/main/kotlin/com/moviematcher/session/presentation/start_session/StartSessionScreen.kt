package com.moviematcher.session.presentation.start_session

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moviematcher.designsystem.R
import com.moviematcher.designsystem.component.button.ClickableIcon
import com.moviematcher.designsystem.theme.MovieMatcherTheme
import com.moviematcher.designsystem.theme.Red70Transparent
import com.moviematcher.designsystem.theme.dimens
import com.moviematcher.designsystem.utils.UiUtils
import com.moviematcher.session.presentation.NFCActivityContract
import com.moviematcher.session.presentation.start_or_join.StartSessionViewModel
import com.moviematcher.session.util.NFCSession
import org.koin.androidx.compose.koinViewModel

@Composable
fun StartSessionScreen(
    viewModel: StartSessionViewModel = koinViewModel(),
    onJoinSession: (String) -> Unit = {},
    onUpdateNFCSession: (NFCSession) -> Unit,
) {
    /*

    val launcher =
        rememberLauncherForActivityResult(contract = NFCActivityContract()) { nfcSession ->
            nfcSession?.let {}
        }
     */

    LaunchedEffect(Unit) {
        viewModel.createNewSession(viewModel.sessionCode)
        viewModel.isGuestReady(viewModel.sessionCode)
        val sessionCode = viewModel.sessionCode
        onUpdateNFCSession(NFCSession(sessionCode))

        /*
        val sessionCode = viewModel.sessionCode
        launcher.launchNFCActivity(
            context = context, session = NFCSession(sessionId = sessionCode)
        )
         */
    }

    val guestUiState by viewModel.guestUiState.collectAsStateWithLifecycle()
    LaunchedEffect(guestUiState) {
        if (guestUiState == true) {
            onJoinSession(viewModel.sessionCode)
        }
    }

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
                text = stringResource(id = R.string.start_session_screen_title),
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
            Footer(sessionCode = viewModel.sessionCode)
        }

    }
}

@Preview
@Composable
private fun StartSessionScreenPreview() {
    MovieMatcherTheme {
        Surface {
            StartSessionScreen(onUpdateNFCSession = {})
        }
    }
}

@Composable
private fun Footer(sessionCode: String) {
    val context = LocalContext.current
    ConstraintLayout {
        val (textField, shareBtn) = createRefs()
        TextField(
            modifier = Modifier
                .constrainAs(textField) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
                .padding(end = MaterialTheme.dimens.medium),
            value = sessionCode,
            onValueChange = {},
            enabled = false,
            readOnly = true,
            trailingIcon = {
                Icon(
                    modifier = Modifier.clickable {
                        UiUtils.copyToClipBoard(
                            context = context,
                            text = sessionCode
                        )
                    },
                    painter = painterResource(id = R.drawable.ic_content_copy),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.background
                )
            },
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = MaterialTheme.colorScheme.onBackground,
                disabledIndicatorColor = Color.Transparent
            ),
        )

        ClickableIcon(
            modifier = Modifier
                .constrainAs(shareBtn) {
                    start.linkTo(textField.end)
                    top.linkTo(textField.top)
                    bottom.linkTo(textField.bottom)
                    height = Dimension.fillToConstraints
                }
                .aspectRatio(1f),
            onClick = {
                UiUtils.shareCode(context = context, text = sessionCode)
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_share),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.background
            )
        }
    }
}
