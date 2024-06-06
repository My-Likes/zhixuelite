package com.zhixue.lite.feature.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhixue.lite.core.designsystem.component.AsyncImage
import com.zhixue.lite.core.designsystem.component.Divider
import com.zhixue.lite.core.designsystem.component.Icon
import com.zhixue.lite.core.designsystem.component.Text
import com.zhixue.lite.core.designsystem.theme.Theme
import com.zhixue.lite.core.model.UserInfo
import com.zhixue.lite.core.common.R as commonR

@Composable
internal fun ProfileRoute(viewModel: ProfileViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProfileScreen(uiState = uiState)
}

@Composable
internal fun ProfileScreen(uiState: ProfileUiState) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        ProfileHeader()
        ProfileContent(uiState = uiState)
    }
}

@Composable
internal fun ProfileHeader() {
    Column {
        Spacer(modifier = Modifier.height(64.dp))
        Text(
            text = stringResource(R.string.profile_title),
            color = Theme.colorScheme.onBackground,
            style = Theme.typography.headline,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}

@Composable
internal fun ProfileContent(uiState: ProfileUiState) {
    if (uiState is ProfileUiState.Success) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .border(1.dp, Theme.colorScheme.outline, Theme.shapes.medium)
                .clip(Theme.shapes.medium)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            ProfilePanel(userInfo = uiState.userInfo)
            Divider()
            SettingPanel()
        }
    }
}

@Composable
internal fun ProfilePanel(userInfo: UserInfo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            imageUrl = userInfo.avatar,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(24.dp))
        Column {
            Text(
                text = userInfo.name,
                color = Theme.colorScheme.onBackground,
                style = Theme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${userInfo.schoolName} ${userInfo.className}",
                color = Theme.colorScheme.onBackground,
                style = Theme.typography.bodySmall.copy(fontWeight = FontWeight.Light)
            )
        }
    }
}

@Composable
internal fun SettingPanel() {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        Text(
            text = stringResource(R.string.profile_account_label),
            color = Theme.colorScheme.onBackgroundVariant,
            style = Theme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        )
        SettingItem(
            title = stringResource(R.string.profile_switch_account),
            onClick = { /*TODO*/ }
        )
        SettingItem(
            title = stringResource(R.string.profile_modify_password),
            onClick = { /*TODO*/ }
        )
        Divider(modifier = Modifier.padding(top = 8.dp, bottom = 16.dp))
        Text(
            text = stringResource(R.string.profile_general_label),
            color = Theme.colorScheme.onBackgroundVariant,
            style = Theme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        )
        SettingItem(
            title = stringResource(R.string.profile_check_update),
            onClick = { /*TODO*/ }
        )
        SettingItem(
            title = stringResource(R.string.profile_feedback),
            onClick = { /*TODO*/ }
        )
        SettingItem(
            title = stringResource(R.string.profile_clear_cache),
            onClick = { /*TODO*/ }
        )
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        SettingItem(
            title = stringResource(R.string.profile_logout),
            onClick = { /*TODO*/ },
            color = Theme.colorScheme.error
        )
    }
}

@Composable
internal fun SettingItem(
    title: String,
    onClick: () -> Unit,
    color: Color = Theme.colorScheme.onBackground
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = color,
            style = Theme.typography.titleSmall,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(24.dp))
        Icon(
            painter = painterResource(commonR.drawable.ic_next),
            tint = color,
            modifier = Modifier.size(20.dp)
        )
    }
}