package com.zhixue.lite.core.designsystem.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import com.zhixue.lite.core.designsystem.theme.Theme

@Composable
fun AsyncImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    placeholder: Painter = ColorPainter(Theme.colorScheme.container),
    error: Painter = ColorPainter(Theme.colorScheme.container)
) {
    coil.compose.AsyncImage(
        model = imageUrl,
        contentDescription = null,
        modifier = modifier,
        placeholder = placeholder,
        error = error
    )
}