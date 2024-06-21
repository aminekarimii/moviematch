package com.moviematcher.designsystem.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moviematcher.designsystem.theme.MidnightBlue60
import com.moviematcher.designsystem.theme.White

/*
######################################################
||                                                  ||
||                  Primary Button                  ||
||                                                  ||
######################################################
*/
@Composable
fun getSocialMediaColorScheme() = ButtonDefaults.buttonColors(
    containerColor = White,
    contentColor = MidnightBlue60
)

internal val buttonHeight = 44.dp

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .height(buttonHeight)
            .then(modifier),
        shape = MaterialTheme.shapes.small,
        contentPadding = PaddingValues(),
        colors = colors,
        onClick = onClick,
        enabled = enabled,
    ) {
        Text(
            style = MaterialTheme.typography.titleSmall,
            text = text,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = Modifier
            .height(buttonHeight)
            .then(modifier),
        colors = colors,
        shape = MaterialTheme.shapes.small,
        contentPadding = PaddingValues(),
        onClick = onClick,
        enabled = enabled,
        content = content
    )
}

/*
######################################################
||                                                  ||
||                 Secondary Button                 ||
||                    Text only                     ||
||                                                  ||
######################################################
*/

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    TextButton(
        modifier = Modifier
            .height(buttonHeight)
            .then(modifier),
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        enabled = enabled
    ) {
        Text(text = text, color = MaterialTheme.colorScheme.onSecondary)
    }
}