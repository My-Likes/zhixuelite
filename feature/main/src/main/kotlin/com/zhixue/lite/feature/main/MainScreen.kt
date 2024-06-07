package com.zhixue.lite.feature.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import com.zhixue.lite.core.designsystem.component.Divider
import com.zhixue.lite.core.designsystem.component.Icon
import com.zhixue.lite.core.designsystem.component.IconButton
import com.zhixue.lite.core.designsystem.theme.Theme
import com.zhixue.lite.feature.home.navigation.HomeRoute
import com.zhixue.lite.feature.home.navigation.homeScreen
import com.zhixue.lite.feature.main.navigation.MainDestination
import com.zhixue.lite.feature.profile.navigation.profileScreen

@Composable
internal fun MainRoute(
    onLogoutClick: () -> Unit,
    onReportInfoClick: (String) -> Unit,
    mainState: MainState = rememberMainState()
) {
    MainScreen(
        mainState = mainState,
        onLogoutClick = onLogoutClick,
        onReportInfoClick = onReportInfoClick
    )
}

@Composable
internal fun MainScreen(
    mainState: MainState,
    onLogoutClick: () -> Unit,
    onReportInfoClick: (String) -> Unit
) {
    Column {
        NavHost(
            navController = mainState.navController,
            startDestination = HomeRoute,
            modifier = Modifier.weight(1f),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            homeScreen(onReportInfoClick = onReportInfoClick)
            profileScreen(onLogoutClick = onLogoutClick)
        }
        Divider()
        MainBottomBar(
            destinations = mainState.destinations,
            currentDestination = mainState.currentDestination,
            onDestinationClick = mainState::navigateToDestination,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 4.dp)
        )
    }
}

@Composable
internal fun MainBottomBar(
    destinations: List<MainDestination>,
    currentDestination: NavDestination?,
    onDestinationClick: (MainDestination) -> Unit,
    modifier: Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        destinations.forEach { destination ->
            val isSelected =
                currentDestination?.route?.contains(destination.name, ignoreCase = true) ?: false
            IconButton(
                onClick = { onDestinationClick(destination) },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(destination.iconId),
                    tint = if (isSelected) Theme.colorScheme.primary else Theme.colorScheme.onBackgroundVariant
                )
            }
        }
    }
}