package com.moviematcher.matching.presentation.match

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp


enum class SwipingDirection {
    Left, Right
}


@Composable
fun rememberSwipeableCardState(): SwipeableCardState {
    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }
    return remember() {
        SwipeableCardState(screenWidth)
    }
}

class SwipeableCardState(
    internal val maxWidth: Float,
) {
    val offset = Animatable(offset(0f, 0f), Offset.VectorConverter)

    /**
     * The [SwipingDirection] the card was swiped at.
     *
     * Null value means the card has not been swiped fully yet.
     */
    private var swipedDirection: SwipingDirection? by mutableStateOf(null)

    internal suspend fun reset() {
        offset.animateTo(offset(0f, 0f), tween(200))
    }

    suspend fun swipe(direction: SwipingDirection, animationSpec: AnimationSpec<Offset> = tween(300)) {
        val endX = maxWidth
        when (direction) {
            SwipingDirection.Left -> offset.animateTo(offset(x = -endX), animationSpec)
            SwipingDirection.Right -> offset.animateTo(offset(x = endX), animationSpec)
            else -> Unit
        }
        this.swipedDirection = direction
    }

    private fun offset(x: Float = offset.value.x, y: Float = offset.value.y): Offset {
        return Offset(x, y)
    }

     suspend fun drag(x: Float) {
        offset.animateTo(offset(x, 0f))
    }
}