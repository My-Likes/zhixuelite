package com.zhixue.lite.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.zhixue.lite.LoginState
import com.zhixue.lite.feature.login.navigation.LoginRoute
import com.zhixue.lite.feature.login.navigation.navigateToLogin
import com.zhixue.lite.feature.main.navigation.MainRoute
import com.zhixue.lite.feature.main.navigation.navigateToMain
import com.zhixue.lite.feature.report.navigation.navigateToReport

@Composable
fun rememberAppState(
    loginState: LoginState,
    navController: NavHostController = rememberNavController()
): ZhixueLiteAppState {
    return remember(loginState, navController) {
        ZhixueLiteAppState(
            loginState = loginState,
            navController = navController
        )
    }
}

@Stable
class ZhixueLiteAppState(
    loginState: LoginState,
    val navController: NavHostController
) {
    val currentStartDestination: Any = when (loginState) {
        LoginState.Loading -> Unit
        LoginState.LoggedIn -> MainRoute
        LoginState.NotLoggedIn -> LoginRoute
    }

    fun navigateToMain() {
        val navOptions = navOptions {
            launchSingleTop = true
        }
        navController.popBackStack()
        navController.navigateToMain(navOptions)
    }

    fun navigateToLogin() {
        val navOptions = navOptions {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
            launchSingleTop = true
        }
        navController.navigateToLogin(navOptions)
    }

    fun navigateToReportDetail(reportId: String) {
        val navOptions = navOptions {
            launchSingleTop = true
        }
        navController.navigateToReport(reportId, navOptions)
    }

    fun popBackStack() {
        navController.popBackStack()
    }
}