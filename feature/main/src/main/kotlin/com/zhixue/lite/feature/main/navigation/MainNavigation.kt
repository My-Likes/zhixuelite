package com.zhixue.lite.feature.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.zhixue.lite.feature.main.MainRoute
import kotlinx.serialization.Serializable

@Serializable
object MainRoute

fun NavController.navigateToMain(navOptions: NavOptions? = null) {
    navigate(MainRoute, navOptions)
}

fun NavGraphBuilder.mainScreen(
    onLogoutClick: () -> Unit,
    onReportInfoClick: (String) -> Unit
) {
    composable<MainRoute> {
        MainRoute(
            onLogoutClick = onLogoutClick,
            onReportInfoClick = onReportInfoClick
        )
    }
}