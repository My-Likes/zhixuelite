package com.zhixue.lite.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.zhixue.lite.feature.login.navigation.loginScreen
import com.zhixue.lite.feature.main.navigation.mainScreen
import com.zhixue.lite.feature.report.navigation.reportScreen
import com.zhixue.lite.ui.ZhixueLiteAppState

@Composable
fun ZhixueLiteNavHost(
    appState: ZhixueLiteAppState,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = appState.navController,
        startDestination = appState.currentStartDestination,
        modifier = modifier,
        enterTransition = { slideInHorizontally() + fadeIn(animationSpec = tween(100)) },
        exitTransition = { slideOutHorizontally() + fadeOut(animationSpec = tween(100)) }
    ) {
        loginScreen(
            onLoginSuccess = appState::navigateToMain,
            onRegisterClick = {},
            onForgetPasswordClick = {}
        )
        mainScreen(onReportInfoClick = appState::navigateToReportDetail)
        reportScreen(
            onBackClick = appState::popBackStack,
            onPaperInfoClick = {}
        )
    }
}