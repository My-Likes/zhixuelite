package com.zhixue.lite.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zhixue.lite.core.designsystem.component.Icon
import com.zhixue.lite.core.designsystem.component.ProgressBar
import com.zhixue.lite.core.designsystem.component.Text
import com.zhixue.lite.core.designsystem.modifier.themePlaceholder
import com.zhixue.lite.core.designsystem.theme.Theme
import com.zhixue.lite.core.designsystem.theme.ZhixueLiteTheme
import com.zhixue.lite.core.model.FormatPaperInfo
import com.zhixue.lite.core.model.TrendDirection
import com.zhixue.lite.core.common.R as commonR

@Composable
fun PaperInfoItem(
    modifier: Modifier = Modifier,
    paperInfo: FormatPaperInfo? = null,
    enabledPlaceholder: Boolean = false
) {
    Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Row(
            modifier = Modifier.padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        append(paperInfo?.subjectName)
                        append(" ")
                        withStyle(Theme.typography.bodySmall.toSpanStyle()) {
                            append(paperInfo?.level)
                        }
                    },
                    color = Theme.colorScheme.onBackground,
                    style = Theme.typography.bodyMedium,
                    modifier = Modifier
                        .width(if (enabledPlaceholder) 128.dp else Dp.Unspecified)
                        .themePlaceholder(enabledPlaceholder)
                )
                Spacer(modifier = Modifier.width(8.dp))
                TrendDirectionIcon(
                    trendDirection = paperInfo?.direction,
                    modifier = Modifier
                        .size(12.dp)
                        .themePlaceholder(enabledPlaceholder)
                )
            }
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                text = "${paperInfo?.userScore} / ${paperInfo?.standardScore}",
                color = Theme.colorScheme.onBackgroundVariant,
                style = Theme.typography.bodySmall,
                modifier = Modifier
                    .width(if (enabledPlaceholder) 48.dp else Dp.Unspecified)
                    .themePlaceholder(enabledPlaceholder)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.weight(1f)) {
                Text(
                    text = "班级排名：${paperInfo?.classRank}",
                    color = Theme.colorScheme.onBackgroundVariant,
                    style = Theme.typography.bodySmall.copy(fontWeight = FontWeight.Light),
                    modifier = Modifier
                        .width(if (enabledPlaceholder) 64.dp else Dp.Unspecified)
                        .themePlaceholder(enabledPlaceholder)
                )
            }
            Spacer(modifier = Modifier.width(24.dp))
            ProgressBar(
                value = paperInfo?.scoreRate ?: 0f,
                modifier = Modifier
                    .width(48.dp)
                    .height(6.dp)
                    .themePlaceholder(enabledPlaceholder)
            )
        }
    }
}

@Composable
private fun TrendDirectionIcon(
    trendDirection: TrendDirection?,
    modifier: Modifier = Modifier
) {
    val (iconId, tint) = when (trendDirection) {
        TrendDirection.UP -> commonR.drawable.ic_trending_up to Theme.colorScheme.primary
        TrendDirection.DOWN -> commonR.drawable.ic_trending_down to Theme.colorScheme.error
        else -> commonR.drawable.ic_trending_flat to Theme.colorScheme.onBackgroundVariant
    }
    Icon(
        painter = painterResource(iconId),
        tint = tint,
        modifier = modifier
    )
}

@Preview
@Composable
private fun PaperInfoItemPreview() {
    ZhixueLiteTheme {
        PaperInfoItem(enabledPlaceholder = false)
    }
}