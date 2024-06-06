package com.moviematcher.authentication.login.presentation

import androidx.compose.foundation.Image
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moviematcher.designsystem.R
import com.moviematcher.designsystem.component.button.PrimaryButton
import com.moviematcher.designsystem.component.button.SecondaryButton
import com.moviematcher.designsystem.component.button.getSocialMediaColorScheme
import com.moviematcher.designsystem.theme.LocalDimens
import com.moviematcher.designsystem.theme.MovieMatcherTheme
import com.moviematcher.designsystem.theme.dimens

@Composable
fun LoginRoute() {
    LoginScreen(
        viewState = LoginScreenViewState(),
        onSignInClicked = {},
        onSignInAsGuestClicked = {},
        onTermOfServicesClicked = {},
        onPrivacyPolicyClicked = {}
    )
}

@Composable
fun LoginScreen(
    viewState: LoginScreenViewState,
    onSignInClicked: () -> Unit,
    onSignInAsGuestClicked: () -> Unit,
    onTermOfServicesClicked: () -> Unit,
    onPrivacyPolicyClicked: () -> Unit,
) {
    Surface {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = MaterialTheme.dimens.screenPaddingHorizontal)
                .padding(top = 70.dp, bottom =  MaterialTheme.dimens.large)
                .verticalScroll(rememberScrollState()),
        ) {
            Column(
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
                Spacer(modifier = Modifier.height( MaterialTheme.dimens.extraBig))
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    painter = painterResource(id = R.drawable.img_login),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
                Spacer(modifier = Modifier.height( MaterialTheme.dimens.extraBig))
                Buttons(
                    onSignInClicked = onSignInClicked,
                    onSignInAsGuestClicked = onSignInAsGuestClicked
                )
            }
            Footer(
                modifier = Modifier.align(Alignment.BottomCenter),
                onTermOfServicesClicked = onTermOfServicesClicked,
                onPrivacyPolicyClicked = onPrivacyPolicyClicked
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    MovieMatcherTheme {
        LoginScreen(
            viewState = LoginScreenViewState(),
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