package com.moviematcher.session.presentation.start_session

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.moviematcher.designsystem.R
import com.moviematcher.designsystem.component.button.ClickableIcon
import com.moviematcher.designsystem.component.button.PrimaryButton
import com.moviematcher.designsystem.component.button.getSocialMediaColorScheme
import com.moviematcher.designsystem.theme.MovieMatcherTheme
import com.moviematcher.designsystem.theme.Red70Transparent
import com.moviematcher.designsystem.theme.dimens
import com.moviematcher.designsystem.utils.UiUtils
import com.moviematcher.session.presentation.start_or_join.StartSessionViewModel
import com.moviematcher.session.util.NFCSession
import org.koin.androidx.compose.koinViewModel

@Composable
fun StartSessionScreen(
    viewModel: StartSessionViewModel = koinViewModel(),
    onJoinSession: (String) -> Unit = {},
) {
    var showQRCode by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.createNewSession(viewModel.sessionCode)
        viewModel.isGuestReady(viewModel.sessionCode)
    }

    val guestUiState by viewModel.guestUiState.collectAsStateWithLifecycle()
    LaunchedEffect(guestUiState) {
        if (guestUiState == true) {
            onJoinSession(viewModel.sessionCode)
        }
    }

    Box(modifier = Modifier.padding(bottom = MaterialTheme.dimens.screenPaddingVertical)) {
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
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.big * 3))
        }

        PrimaryButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = MaterialTheme.dimens.screenPaddingHorizontal)
                .fillMaxWidth(),
            colors = getSocialMediaColorScheme().copy(contentColor = MaterialTheme.colorScheme.background),
            onClick = { showQRCode = !showQRCode }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_share_qr_code),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(MaterialTheme.dimens.large))
            Text(text = stringResource(id = R.string.start_session_show_qr_code_title))
        }

        if (showQRCode) {
            val sessionCode = viewModel.sessionCode
            QRCodePopup(sessionId = sessionCode, onDismiss = {
                showQRCode = false
            })
        }
    }
}

@Composable
fun QRCodePopup(sessionId: String, onDismiss: () -> Unit) {
    BackHandler {
        onDismiss()
    }

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    onDismiss()
                })
            })
        Surface(
            modifier = Modifier.wrapContentSize(),
            shape = MaterialTheme.shapes.medium,
            color = Color.Transparent.copy(0.3F)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val writer = QRCodeWriter()
                val jsonSession = Gson().toJson(NFCSession(sessionId))
                val bitMatrix = writer.encode(jsonSession, BarcodeFormat.QR_CODE, 512, 512)
                val encoder = BarcodeEncoder()
                val bitmap = encoder.createBitmap(bitMatrix)

                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "QR Code",
                    modifier = Modifier.size(300.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun StartSessionScreenPreview() {
    MovieMatcherTheme {
        Surface {
            StartSessionScreen()
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
