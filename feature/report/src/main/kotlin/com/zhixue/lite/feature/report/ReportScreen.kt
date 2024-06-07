package com.zhixue.lite.feature.report

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zhixue.lite.core.designsystem.component.Divider
import com.zhixue.lite.core.designsystem.component.Icon
import com.zhixue.lite.core.designsystem.component.IconButton
import com.zhixue.lite.core.designsystem.component.Text
import com.zhixue.lite.core.designsystem.modifier.themePlaceholder
import com.zhixue.lite.core.designsystem.theme.Theme
import com.zhixue.lite.core.model.FormatPaperInfo
import com.zhixue.lite.core.model.ReportDetail
import com.zhixue.lite.core.ui.PaperInfoItem
import com.zhixue.lite.core.ui.ScorePanel
import com.zhixue.lite.core.common.R as commonR

@Composable
internal fun ReportRoute(
    viewModel: ReportViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onPaperInfoClick: (String) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    ReportScreen(
        uiState = viewModel.uiState,
        onBackClick = onBackClick,
        onPaperClick = onPaperInfoClick
    )
}

@Composable
internal fun ReportScreen(
    uiState: ReportUiState,
    onBackClick: () -> Unit,
    onPaperClick: (String) -> Unit
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        ReportHeader(onBackClick = onBackClick)
        ReportContent(
            uiState = uiState,
            onPaperClick = onPaperClick
        )
    }
}

@Composable
internal fun ReportHeader(onBackClick: () -> Unit) {
    Column {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                painter = painterResource(commonR.drawable.ic_back),
                tint = Theme.colorScheme.onBackground
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.report_title),
            color = Theme.colorScheme.onBackground,
            style = Theme.typography.headline,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}

@Composable
internal fun ReportContent(uiState: ReportUiState, onPaperClick: (String) -> Unit) {
    Crossfade(
        label = "UiState",
        targetState = uiState
    ) { targetUiState ->
        when (targetUiState) {
            ReportUiState.Loading -> ReportPlaceholderBody()
            ReportUiState.Failure -> ReportErrorBody()
            is ReportUiState.Success -> ReportDetailBody(
                reportDetail = targetUiState.reportDetail,
                onPaperClick = onPaperClick
            )
        }
    }
}

@Composable
internal fun ReportPlaceholderBody() {
    Column(
        modifier = Modifier
            .padding(24.dp)
            .border(1.dp, Theme.colorScheme.outline, Theme.shapes.medium)
    ) {
        ScorePanel(enabledPlaceholder = true)
        Spacer(Modifier.height(1.dp))
        OverviewPanel(enabledPlaceholder = true)
    }
}

@Composable
internal fun ReportErrorBody() {
    Box(modifier = Modifier.padding(horizontal = 32.dp)) {
        Text(
            text = stringResource(R.string.report_error_message),
            color = Theme.colorScheme.error,
            style = Theme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Composable
internal fun ReportDetailBody(
    reportDetail: ReportDetail,
    onPaperClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(24.dp)
            .border(1.dp, Theme.colorScheme.outline, Theme.shapes.medium)
    ) {
        ScorePanel(
            label = stringResource(R.string.report_total_score_label),
            scoreInfo = reportDetail.scoreInfo
        )
        Divider()
        OverviewPanel(
            overview = reportDetail.overviews,
            onPaperClick = onPaperClick
        )
    }
}

@Composable
internal fun OverviewPanel(
    overview: List<FormatPaperInfo> = emptyList(),
    onPaperClick: (String) -> Unit = {},
    enabledPlaceholder: Boolean = false
) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        Text(
            text = stringResource(R.string.report_overview_label),
            color = Theme.colorScheme.onBackgroundVariant,
            style = Theme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .width(if (enabledPlaceholder) 48.dp else Dp.Unspecified)
                .themePlaceholder(enabledPlaceholder)
        )
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            if (enabledPlaceholder) {
                repeat(5) {
                    PaperInfoItem(
                        enabledPlaceholder = true,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            } else {
                overview.forEach { paperInfo ->
                    PaperInfoItem(
                        paperInfo = paperInfo,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clip(Theme.shapes.small)
                            .clickable { onPaperClick(paperInfo.id) }
                    )
                }
            }
        }
    }
}