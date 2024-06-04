package com.zhixue.lite.feature.home

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.zhixue.lite.core.designsystem.component.Divider
import com.zhixue.lite.core.designsystem.component.Icon
import com.zhixue.lite.core.designsystem.component.Text
import com.zhixue.lite.core.designsystem.component.TextButton
import com.zhixue.lite.core.designsystem.modifier.placeholder
import com.zhixue.lite.core.designsystem.theme.Theme
import com.zhixue.lite.core.model.FormatReportInfo
import com.zhixue.lite.core.common.R as commonR

@Composable
internal fun HomeRoute(
    onReportInfoClick: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    homeState: HomeState = rememberHomeState()
) {
    val context = LocalContext.current

    HomeScreen(
        homeState = homeState,
        examReportInfoList = viewModel.examReportInfoList.collectAsLazyPagingItems(),
        homeworkReportInfoList = viewModel.homeworkReportInfoList.collectAsLazyPagingItems(),
        onReportInfoClick = { reportId, isPublished ->
            if (isPublished) {
                onReportInfoClick(reportId)
            } else {
                Toast.makeText(context, R.string.home_not_supported, Toast.LENGTH_SHORT).show()
            }
        }
    )
}

@Composable
internal fun HomeScreen(
    homeState: HomeState,
    examReportInfoList: LazyPagingItems<FormatReportInfo>,
    homeworkReportInfoList: LazyPagingItems<FormatReportInfo>,
    onReportInfoClick: (String, Boolean) -> Unit
) {
    Column {
        HomeTabs(
            pageTitleIds = homeState.pageTitleIds,
            currentPageIndex = homeState.currentPageIndex,
            onTabClick = homeState::scrollToPage
        )
        Divider()
        HorizontalPager(homeState.pagerState) { index ->
            HomeReportInfoPage(
                reportInfoList = when (homeState.pages[index]) {
                    HomePage.EXAM -> examReportInfoList
                    HomePage.HOMEWORK -> homeworkReportInfoList
                },
                onReportInfoClick = onReportInfoClick
            )
        }
    }
}

@Composable
internal fun HomeTabs(
    pageTitleIds: List<Int>,
    currentPageIndex: Int,
    onTabClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
    ) {
        pageTitleIds.forEachIndexed { index, pageTitleId ->
            TextButton(onClick = { onTabClick(index) }) {
                Text(
                    text = stringResource(pageTitleId),
                    color = if (index == currentPageIndex) Theme.colorScheme.primary else Theme.colorScheme.onBackgroundVariant,
                    style = Theme.typography.titleSmall
                )
            }
        }
    }
}

@Composable
internal fun HomeReportInfoPage(
    reportInfoList: LazyPagingItems<FormatReportInfo>,
    onReportInfoClick: (String, Boolean) -> Unit
) {
    Crossfade(
        label = "Loading",
        targetState = reportInfoList.loadState.refresh is LoadState.Loading,
        animationSpec = tween(800)
    ) { isLoading ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            userScrollEnabled = !isLoading
        ) {
            if (isLoading) {
                placeholderBody()
            } else {
                reportInfoBody(reportInfoList, onReportInfoClick)
            }
        }
    }
}

internal fun LazyListScope.placeholderBody() {
    items(20) {
        ReportInfoPlaceHolderItem()
    }
}

internal fun LazyListScope.reportInfoBody(
    reportInfoList: LazyPagingItems<FormatReportInfo>,
    onReportInfoClick: (String, Boolean) -> Unit
) {
    items(
        count = reportInfoList.itemCount,
        key = reportInfoList.itemKey()
    ) { index ->
        val reportInfo = reportInfoList[index]
        if (reportInfo != null) {
            ReportInfoItem(
                name = reportInfo.name,
                createDate = reportInfo.createDate,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .clip(Theme.shapes.small)
                    .clickable { onReportInfoClick(reportInfo.id, reportInfo.isPublished) }
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
        } else {
            ReportInfoPlaceHolderItem()
        }
    }
}

@Composable
internal fun ReportInfoItem(
    modifier: Modifier = Modifier,
    name: String = "",
    createDate: String = "",
    enabledPlaceholder: Boolean = false
) {
    val placeholderModifier = Modifier
        .placeholder(
            visible = enabledPlaceholder,
            color = Theme.colorScheme.container,
            highlightColor = Theme.colorScheme.background,
            shape = Theme.shapes.medium
        )

    Box(modifier = modifier) {
        Row(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = name,
                    color = Theme.colorScheme.onBackground,
                    style = Theme.typography.bodyMedium,
                    modifier = Modifier
                        .defaultMinSize(minWidth = 168.dp)
                        .then(placeholderModifier)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = createDate,
                    color = Theme.colorScheme.onBackgroundVariant,
                    style = Theme.typography.bodySmall.copy(fontWeight = FontWeight.Light),
                    modifier = Modifier
                        .defaultMinSize(minWidth = 64.dp)
                        .then(placeholderModifier)
                )
            }
            Spacer(modifier = Modifier.width(24.dp))
            Icon(
                painter = painterResource(commonR.drawable.ic_next),
                tint = Theme.colorScheme.onBackgroundVariant,
                modifier = Modifier.then(placeholderModifier)
            )
        }
    }
}

@Composable
internal fun ReportInfoPlaceHolderItem() {
    ReportInfoItem(
        enabledPlaceholder = true,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    )
    Spacer(modifier = Modifier.height(1.dp))
}