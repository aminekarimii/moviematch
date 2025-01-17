package com.moviematcher.session.presentation.join_session

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.gson.JsonParser
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView
import com.moviematcher.designsystem.R
import com.moviematcher.designsystem.component.button.ClickableIcon
import com.moviematcher.designsystem.component.button.PrimaryButton
import com.moviematcher.designsystem.theme.MovieMatcherTheme
import com.moviematcher.designsystem.theme.Red70Transparent
import com.moviematcher.designsystem.theme.dimens
import org.koin.androidx.compose.koinViewModel

@Composable
fun JoinSessionScreen(
    viewModel: JoinSessionViewModel = koinViewModel(),
    onJoinSession: (String) -> Unit,
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var sessionCode by remember { mutableStateOf("") }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    var qrCodeReaderError by remember { mutableStateOf(false) }
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    var showScanner by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasCameraPermission = isGranted
        if (isGranted) {
            showScanner = true
        }
    }

    BackHandler(showScanner) {
        showScanner = false
    }

    LaunchedEffect(key1 = true) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is JoinSessionUiState.StartMatch -> onJoinSession(sessionCode)
            is JoinSessionUiState.Error -> {
                Toast.makeText(context, uiState.message, Toast.LENGTH_SHORT).show()
            }

            else -> Unit
        }
    }
    if (showScanner && hasCameraPermission) {
        AndroidView(
            factory = { context ->
                CompoundBarcodeView(context).apply {
                    val capture = CaptureManager(context as Activity, this)
                    capture.initializeFromIntent(context.intent, null)
                    capture.decode()
                    this.decodeContinuous { result ->
                        val sessionId = extractSessionIdFromQRCode(result.text)
                        qrCodeReaderError = false
                        showScanner = false
                        if (sessionId != null) {
                            qrCodeReaderError = false
                            sessionCode = sessionId
                        } else {
                            qrCodeReaderError = true
                            sessionCode = ""
                        }
                    }
                    resume()
                }
            }, modifier = Modifier.fillMaxSize()
        )
    } else {
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
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.dimens.screenPaddingHorizontal)
                ) {
                    val (textField, icon) = createRefs()

                    TextField(
                        modifier = Modifier
                            .constrainAs(textField) {
                                start.linkTo(parent.start)
                                end.linkTo(icon.start)
                                width = Dimension.percent(0.8f) // 80% width
                            }
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

                    ClickableIcon(
                        modifier = Modifier
                            .constrainAs(icon) {
                                start.linkTo(textField.end)
                                end.linkTo(parent.end)
                                top.linkTo(textField.top)
                                bottom.linkTo(textField.bottom)
                                width = Dimension.percent(0.2f)
                            }
                            .aspectRatio(1f)
                            .padding(start = 8.dp, end = 0.dp, top = 8.dp, bottom = 8.dp),
                        onClick = {
                            if (hasCameraPermission) {
                                showScanner = true
                            } else {
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_qr_code),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.background
                        )
                    }
                }

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.big))
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.dimens.screenPaddingHorizontal),
                    text = stringResource(id = R.string.start_or_join_session_screen_join_session),
                    enabled = sessionCode.isNotBlank()
                ) {
                    viewModel.onJoinSession(sessionCode)
                }
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    painter = painterResource(id = com.moviematcher.session.R.drawable.img_waves),
                    contentDescription = null
                )
                SideEffect {
                    focusRequester.requestFocus()
                }
                if (qrCodeReaderError) {
                    Toast.makeText(context, "Can't scan this qr code", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

fun extractSessionIdFromQRCode(qrCodeText: String): String? {
    return try {
        val jsonElement = JsonParser.parseString(qrCodeText)
        jsonElement.asJsonObject.get("sessionId")?.asString
    } catch (e: Exception) {
        Log.e("QRCode", "Failed to parse QR code text: $qrCodeText", e)
        null
    }
}

@Preview
@Composable
private fun JoinSessionScreenPreview() {
    MovieMatcherTheme {
        Surface {
            JoinSessionScreen(onJoinSession = {})
        }
    }
}
