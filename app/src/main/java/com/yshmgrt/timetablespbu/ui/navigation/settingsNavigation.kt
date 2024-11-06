package com.yshmgrt.timetablespbu.ui.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.yshmgrt.timetablespbu.ui.navigation.Settings
import com.yshmgrt.timetablespbu.ui.settings.BanScreen
import com.yshmgrt.timetablespbu.ui.settings.Settings
import com.yshmgrt.timetablespbu.ui.settings.TimetableScreen
import kotlinx.serialization.Serializable

sealed interface SettingsDestination {
    @Serializable
    data object Main : SettingsDestination

    @Serializable
    data object Timetable : SettingsDestination

    @Serializable
    data object Ban : SettingsDestination
}

@Serializable
data object Settings

fun NavGraphBuilder.settingsNavigation(navController: NavController) =
    navigation<Settings>(startDestination = SettingsDestination.Main) {
        composable<SettingsDestination.Main> {
            Settings(
                onBackPressed = {
                    Log.d("Navigation", "Settings -> Up")
                    navController.popBackStack()
                },
                onSetup = {
                    Log.d("Navigation", "Settings -> Setup")
                    navController.navigate(SetupWizard)
                },
                onOpenBanSettings = {
                    Log.d("Navigation", "Settings -> Bans")
                    navController.navigate(SettingsDestination.Ban)
                },
                onOpenTimetableSettings = {
                    Log.d("Navigation", "Settings -> Timetable")
                    navController.navigate(SettingsDestination.Timetable)
                }
            )
        }
        composable<SettingsDestination.Timetable> {
            TimetableScreen(
                onBackPressed = {
                    Log.d("Navigation", "Settings: timetable -> Up")
                    navController.popBackStack()
                },
                onTimetableSelected = {
                    Log.d("Navigation", "Settings: timetable picked -> timetable")
                    navController.navigate(Timetable(it.id)) {
                        launchSingleTop = true
                        popUpTo<Settings> {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<SettingsDestination.Ban> {
            BanScreen(onBackPressed = {
                Log.d("Navigation", "Navigation: ban -> Up")
                navController.popBackStack()
            })
        }
    }