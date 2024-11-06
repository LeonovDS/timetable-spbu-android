package com.yshmgrt.timetablespbu.ui.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yshmgrt.timetablespbu.ui.timetable.TimetableScreen
import kotlinx.serialization.Serializable

@Serializable
data class Timetable(val timetableId: Long) : RootNavigationDestination

fun NavGraphBuilder.timetableScreen(navController: NavController) = composable<Timetable> {
    TimetableScreen(
        onSettingsPressed = {
            Log.d("Navigation", "Timetable -> Settings")
            navController.navigate(Settings)
        },
        onSelectLesson = {
            Log.d("Navigation", "Timetable -> Details($it)")
            navController.navigate(Lesson(it.id))
        }
    )
}