package com.zhixue.lite.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zhixue.lite.core.designsystem.component.CircularChart
import com.zhixue.lite.core.designsystem.component.Text
import com.zhixue.lite.core.designsystem.modifier.themePlaceholder
import com.zhixue.lite.core.designsystem.theme.Theme
import com.zhixue.lite.core.designsystem.theme.ZhixueLiteTheme
import com.zhixue.lite.core.model.ScoreInfo

@Composable
fun ScorePanel(
    modifier: Modifier = Modifier,
    label: String = "",
    scoreInfo: ScoreInfo? = null,
    enabledPlaceholder: Boolean = false
) {
    Row(
        modifier = modifier.padding(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                color = Theme.colorScheme.onBackgroundVariant,
                style = Theme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .width(if (enabledPlaceholder) 48.dp else Dp.Unspecified)
                    .themePlaceholder(enabledPlaceholder)
            )
            Text(
                text = buildAnnotatedString {
                    append(scoreInfo?.userScore)
                    withStyle(Theme.typography.bodyLarge.toSpanStyle()) {
                        append(" / ")
                        append(scoreInfo?.standardScore)
                    }
                },
                color = Theme.colorScheme.onBackground,
                style = Theme.typography.display,
                modifier = Modifier
                    .width(if (enabledPlaceholder) 128.dp else Dp.Unspecified)
                    .themePlaceholder(enabledPlaceholder)
            )
        }
        Spacer(modifier = Modifier.width(24.dp))
        CircularChart(
            value = scoreInfo?.scoreRate ?: 0f,
            modifier = Modifier
                .size(56.dp)
                .themePlaceholder(enabledPlaceholder)
        )
    }
}

@Preview
@Composable
private fun ScorePanelPreview() {
    ZhixueLiteTheme {
        ScorePanel(enabledPlaceholder = true)
    }
}