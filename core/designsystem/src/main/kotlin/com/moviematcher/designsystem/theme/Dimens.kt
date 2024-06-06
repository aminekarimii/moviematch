package com.moviematcher.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Dimens(
    val none: Dp = 0.dp,
    val small: Dp = 4.dp,
    val medium: Dp = 8.dp,
    val large: Dp = 12.dp,
    val default: Dp = 16.dp,
    val big: Dp = 20.dp,
    val bigger: Dp = 24.dp,
    val extraBig: Dp = 40.dp,
    val screenPaddingHorizontal: Dp = 20.dp
)

/**
 * A composition local for [Dimens].
 */
val LocalDimens = staticCompositionLocalOf { Dimens() }

val MaterialTheme.dimens
    @Composable
    @ReadOnlyComposable
    get() = LocalDimens.current

