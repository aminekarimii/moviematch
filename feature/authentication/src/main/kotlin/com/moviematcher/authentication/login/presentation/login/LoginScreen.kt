package com.moviematcher.authentication.login.presentation.login

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.moviematcher.designsystem.R
import com.moviematcher.designsystem.component.button.PrimaryButton
import com.moviematcher.designsystem.component.button.SecondaryButton
import com.moviematcher.designsystem.component.button.getSocialMediaColorScheme
import com.moviematcher.designsystem.theme.MovieMatcherTheme
import com.moviematcher.designsystem.theme.dimens
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginRoute(
    onUserLoggedIn: () -> Unit
) {
    val signInClient: GoogleSignInClient = get()
    val loginViewModel: LoginScreenViewModel = koinViewModel()
    val viewState by loginViewModel.viewState.collectAsState()

    LaunchedEffect(key1 = viewState) {
        if (viewState.loggedIn) {
            onUserLoggedIn()
        }
    }

    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = loginViewModel::signIn
    )

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = viewState) {
        if (viewState.errorMsg != null) {
            coroutineScope.launch {
                snackBarHostState.showSnackbar(context.getString(viewState.errorMsg!!))
            }
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) {
                Snackbar(
                    snackbarData = it,
                    containerColor = MaterialTheme.colorScheme.error
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            LoginScreen(
                onSignInClicked = {
                    startForResult.launch(signInClient.signInIntent)
                },
                onSignInAsGuestClicked = {},
                onTermOfServicesClicked = {},
                onPrivacyPolicyClicked = {}
            )
            if (viewState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = SolidColor(Color.Black), alpha = 0.8f)
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

@Composable
fun LoginScreen(
    onSignInClicked: () -> Unit,
    onSignInAsGuestClicked: () -> Unit,
    onTermOfServicesClicked: () -> Unit,
    onPrivacyPolicyClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = MaterialTheme.dimens.screenPaddingHorizontal)
            .padding(top = 70.dp, bottom = MaterialTheme.dimens.default),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(Color.Unspecified)) {
                    append(stringResource(R.string.sign_in_screen_title_part1))
                }
                append(" ")
                withStyle(SpanStyle(MaterialTheme.colorScheme.primary)) {
                    append(stringResource(id = R.string.sign_in_screen_title_part2))
                }
            },
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.extraBig))
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            painter = painterResource(id = R.drawable.img_login),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.extraBig))
        Buttons(
            onSignInClicked = onSignInClicked,
            onSignInAsGuestClicked = onSignInAsGuestClicked
        )
        Footer(
            modifier = Modifier.fillMaxWidth(),
            onTermOfServicesClicked = onTermOfServicesClicked,
            onPrivacyPolicyClicked = onPrivacyPolicyClicked
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    MovieMatcherTheme {
        LoginScreen(
            onSignInClicked = {},
            onSignInAsGuestClicked = {},
            onTermOfServicesClicked = {},
            onPrivacyPolicyClicked = {}
        )
    }
}


@Composable
fun Buttons(
    onSignInClicked: () -> Unit,
    onSignInAsGuestClicked: () -> Unit,
) {
    PrimaryButton(
        modifier = Modifier.fillMaxWidth(),
        colors = getSocialMediaColorScheme(),
        onClick = onSignInClicked,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_google),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width( MaterialTheme.dimens.large))
        Text(text = stringResource(id = R.string.sign_in_screen_google))
    }
    Spacer(modifier = Modifier.height( MaterialTheme.dimens.large))

    SecondaryButton(
        text = stringResource(id = R.string.sign_in_screen_guest),
        onClick = onSignInAsGuestClicked
    )
}

@Composable
fun Footer(
    modifier: Modifier = Modifier,
    onTermOfServicesClicked: () -> Unit,
    onPrivacyPolicyClicked: () -> Unit,
) {
    val tos = stringResource(id = R.string.sign_in_screen_terms_of_services)
    val pp = stringResource(id = R.string.sign_in_screen_privacy_policy)
    val normalSpan = SpanStyle(color = Color(0xFF475467))
    val linkSpan = SpanStyle(
        color = MaterialTheme.colorScheme.onPrimary,
        textDecoration = TextDecoration.Underline
    )
    val footer = buildAnnotatedString {
        withStyle(normalSpan) {
            append(stringResource(id = R.string.sign_in_screen_footer_title))
        }
        withStyle(linkSpan) {
            pushStringAnnotation(tag = tos, annotation = tos)
            append(tos)
        }
        withStyle(normalSpan) {
            append(stringResource(id = R.string.sign_in_screen_footer_and))
        }
        withStyle(linkSpan) {
            pushStringAnnotation(tag = pp, annotation = pp)
            append(pp)
        }
    }
    ClickableText(
        modifier = modifier,
        text = footer,
        style = MaterialTheme.typography.bodySmall.copy(textAlign = TextAlign.Center),
    ) { offset ->
        footer.getStringAnnotations(offset, offset).firstOrNull()?.let { span ->
            if (span.item == tos) {
                onTermOfServicesClicked()
            } else if (span.item == pp) {
                onPrivacyPolicyClicked()
            }
        }
    }
}