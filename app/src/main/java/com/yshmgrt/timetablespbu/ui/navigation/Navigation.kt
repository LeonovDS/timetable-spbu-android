package com.yshmgrt.timetablespbu.ui.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

sealed interface RootNavigationDestination
@Serializable
data object SetupWizard : RootNavigationDestination


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(startDestination: RootNavigationDestination) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = startDestination) {
        setupWizardNavigation(navController)
        timetableScreen(navController)
        lessonDetailsScreen(navController)
        settingsNavigation(navController)
    }
}
