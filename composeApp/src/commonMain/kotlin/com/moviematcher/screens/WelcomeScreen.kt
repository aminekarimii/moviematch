package com.moviematcher.screens

import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import com.moviematcher.resources.MR
import com.moviematcher.theme.MovieMatcherTheme

@Composable
fun WelcomeScreen(
    onSignInWithGoogle: () -> Unit,
    onContinueAsGuest: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 26.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(69.dp))

        Text(
            text = "Can't decide which\nmovie?\nStart a Matcher",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 32.sp
            ),
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 17.dp)
        )

        Spacer(modifier = Modifier.height(53.dp))

        MoviePostersStack()

        Spacer(modifier = Modifier.weight(1f))

        SignInButtons(
            onSignInWithGoogle = onSignInWithGoogle,
            onContinueAsGuest = onContinueAsGuest
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "By signing in you agree to our\nterms of service and privacy policy",
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 12.sp,
                lineHeight = 21.sp
            ),
            color = Color(0xFF475467),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun MoviePostersStack() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        AnimatedImages(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
        )
    }
}

@Composable
private fun SignInButtons(
    onSignInWithGoogle: () -> Unit,
    onContinueAsGuest: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Continue as Guest Button
        SecondaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(MR.strings.sign_in_screen_guest),
            onClick = onContinueAsGuest
        )

    }
}

data class RotationState(val rotation1: Float, val rotation2: Float)

@Composable
fun AnimatedImages(modifier: Modifier = Modifier) {
    var rotateState by remember { mutableStateOf(3f) }
    var offsets by remember { mutableStateOf(Pair(0.dp, 0.dp)) }

    val rotationState by animateValueAsState(
        targetValue = RotationState(rotateState, -rotateState),
        typeConverter = TwoWayConverter(
            convertToVector = { AnimationVector2D(it.rotation1, it.rotation2) },
            convertFromVector = { RotationState(it.v1, it.v2) }
        ),
        animationSpec = tween(durationMillis = 2000)
    )

    val animatedOffsets by animateValueAsState(
        targetValue = offsets,
        typeConverter = TwoWayConverter(
            convertToVector = { AnimationVector2D(it.first.value, it.second.value) },
            convertFromVector = { Pair(it.v1.dp, it.v2.dp) }
        ),
        animationSpec = tween(durationMillis = 2000)
    )

    LaunchedEffect(Unit) {
        rotateState = 5f
        offsets = Pair(10.dp, (-10).dp)
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(MR.images.img2),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .offset(x = animatedOffsets.first)
                .graphicsLayer(
                    rotationZ = rotationState.rotation1
                ),
            contentScale = ContentScale.FillHeight
        )

        Image(
            painter = painterResource(MR.images.img1),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .offset(x = animatedOffsets.second)
                .graphicsLayer(
                    rotationZ = rotationState.rotation2
                ),
            contentScale = ContentScale.FillHeight
        )

        // Middle image with size animation
        Image(
            painter = painterResource(MR.images.img0),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(),
            contentScale = ContentScale.FillHeight
        )
    }
}

