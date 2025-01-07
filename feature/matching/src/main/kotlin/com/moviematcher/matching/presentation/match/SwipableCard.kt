import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import com.moviematcher.matching.presentation.match.SwipeableCardState
import com.moviematcher.matching.presentation.match.SwipingDirection
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun Modifier.swipableCard(
    state: SwipeableCardState,
    onSwiped: (SwipingDirection) -> Unit,
    onSwipeCancel: () -> Unit = {}
): Modifier {
    val scope = rememberCoroutineScope()

    return this
        .pointerInput(Unit) {
            detectHorizontalDragGestures(
                onDragCancel = {
                    scope.launch {
                        state.reset()
                        onSwipeCancel()
                    }
                },
                onDragEnd = {
                    scope.launch {
                        if (hasTravelledEnough(state)) {
                            val swipeDirection = if (state.offset.value.x > 0) {
                                SwipingDirection.Right
                            } else {
                                SwipingDirection.Left
                            }
                            state.swipe(swipeDirection)
                            onSwiped(swipeDirection)
                        } else {
                            state.reset()
                            onSwipeCancel()
                        }
                    }
                },
                onHorizontalDrag = { change, dragAmount ->
                    scope.launch {
                        change.consume()
                        val newX = (state.offset.value.x + dragAmount).coerceIn(-state.maxWidth, state.maxWidth)
                        state.drag(newX)
                    }
                }
            )
        }
        .graphicsLayer {
            translationX = state.offset.value.x
            rotationZ = (state.offset.value.x / 60).coerceIn(-40f, 40f)
            alpha = 1f - (abs(state.offset.value.x) / (state.maxWidth / 2)).coerceIn(0f, 0.5f)
        }
}

private fun hasTravelledEnough(state: SwipeableCardState): Boolean {
    return abs(state.offset.value.x) > state.maxWidth / 10
}

