package com.zhixue.lite.feature.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.zhixue.lite.feature.profile.ProfileRoute
import kotlinx.serialization.Serializable

@Serializable
object ProfileRoute

fun NavController.navigateToProfile(navOptions: NavOptions? = null) {
    navigate(ProfileRoute, navOptions)
}

fun NavGraphBuilder.profileScreen(
    onLogoutClick: () -> Unit
) {
    composable<ProfileRoute> {
        ProfileRoute(
            onLogoutClick = onLogoutClick
        )
    }
}