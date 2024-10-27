package com.yshmgrt.timetablespbu.ui.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.yshmgrt.timetablespbu.model.Lesson
import com.yshmgrt.timetablespbu.ui.main.details.DetailsScreen
import com.yshmgrt.timetablespbu.ui.main.settings.SettingsScreen
import com.yshmgrt.timetablespbu.ui.main.timetable.TimetableScreen
import com.yshmgrt.timetablespbu.ui.main.timetable.TimetableViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun MainScreen(url: String?, timetableId: Int = 0, onSetup: () -> Unit) {
    val navController = rememberNavController()
    if (url == null)
        return
    NavHost(navController, startDestination = MainScreenDestination.Timetable) {
        composable<MainScreenDestination.Timetable> {
            val viewModel = hiltViewModel<TimetableViewModel>()
            TimetableScreen(timetableId, url, viewModel, onSettingsPressed = {
                navController.navigate(MainScreenDestination.Settings)
            }, onSelectLesson = { lesson ->
                navController.navigate(MainScreenDestination.Details(Json.encodeToString(lesson)))
            })
        }
        composable<MainScreenDestination.Settings> {
            SettingsScreen(onSetup = onSetup) {
                navController.popBackStack()
            }
        }
        composable<MainScreenDestination.Details> {
            val details = it.toRoute<MainScreenDestination.Details>()
            val lesson = Json.decodeFromString<Lesson>(details.json)
            DetailsScreen(lesson, onBackPressed = { navController.popBackStack() })
        }
    }
}