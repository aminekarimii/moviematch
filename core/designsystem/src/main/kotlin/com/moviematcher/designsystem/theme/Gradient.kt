package com.moviematcher.designsystem.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val gradient = Brush.verticalGradient(
    colorStops = arrayOf(
        .68f to Color.Transparent,
        .92f to Color.Black
    )
)

val backgroundGradient = Brush.verticalGradient(
    colorStops = arrayOf(
        0f to Color(0xFF080614),
        1f to Color(0xFF110214),
    )
)
