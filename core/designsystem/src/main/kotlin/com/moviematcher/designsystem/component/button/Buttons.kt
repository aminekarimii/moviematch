package com.moviematcher.designsystem.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.moviematcher.designsystem.theme.MidnightBlue60
import com.moviematcher.designsystem.theme.White
import com.moviematcher.designsystem.theme.dimens

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
internal val smallButtonHeight = 32.dp

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    shape: Shape = MaterialTheme.shapes.small,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .height(buttonHeight)
            .then(modifier),
        shape = shape,
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

/*
######################################################
||                                                  ||
||                   Icon Button                    ||
||                    Text only                     ||
||                                                  ||
######################################################
*/

@Composable
fun ClickableIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    trailingIcon: @Composable () -> Unit,
) {
    FilledIconButton(
        modifier = Modifier
            .height(buttonHeight)
            .then(modifier),
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        enabled = enabled,
        content = trailingIcon
    )
}

@Composable
fun LeadingIconButton(
    modifier : Modifier = Modifier,
    title: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .heightIn(min = smallButtonHeight)
            .then(modifier),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0x76475467),
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(50),
        contentPadding = PaddingValues(
            horizontal = MaterialTheme.dimens.large,
            vertical = MaterialTheme.dimens.small
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(MaterialTheme.dimens.small))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}
