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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zhixue.lite.core.designsystem.component.Icon
import com.zhixue.lite.core.designsystem.component.Text
import com.zhixue.lite.core.designsystem.modifier.themePlaceholder
import com.zhixue.lite.core.designsystem.theme.Theme
import com.zhixue.lite.core.designsystem.theme.ZhixueLiteTheme
import com.zhixue.lite.core.common.R as commonR

@Composable
fun ReportInfoItem(
    modifier: Modifier = Modifier,
    name: String = "",
    createDate: String = "",
    enabledPlaceholder: Boolean = false
) {
    Row(
        modifier = modifier.padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                color = Theme.colorScheme.onBackground,
                style = Theme.typography.bodyMedium,
                modifier = Modifier
                    .width(if (enabledPlaceholder) 168.dp else Dp.Unspecified)
                    .padding(vertical = 4.dp)
                    .themePlaceholder(enabledPlaceholder)
            )
            Text(
                text = createDate,
                color = Theme.colorScheme.onBackgroundVariant,
                style = Theme.typography.bodySmall.copy(fontWeight = FontWeight.Light),
                modifier = Modifier
                    .width(if (enabledPlaceholder) 64.dp else Dp.Unspecified)
                    .themePlaceholder(enabledPlaceholder)
            )
        }
        Spacer(modifier = Modifier.width(24.dp))
        Icon(
            painter = painterResource(commonR.drawable.ic_next),
            tint = Theme.colorScheme.onBackgroundVariant,
            modifier = Modifier
                .size(24.dp)
                .themePlaceholder(enabledPlaceholder)
        )
    }
}

@Preview
@Composable
private fun ReportInfoItemPreview() {
    ZhixueLiteTheme {
        ReportInfoItem(enabledPlaceholder = true)
    }
}