package com.yshmgrt.timetablespbu.ui.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yshmgrt.timetablespbu.ui.details.DetailsScreen
import kotlinx.serialization.Serializable

@Serializable
data class Lesson(val lessonId: Long) : RootNavigationDestination

fun NavGraphBuilder.lessonDetailsScreen(navController: NavController) = composable<Lesson> {
    DetailsScreen(onBackPressed = {
        Log.d("Navigation", "LessonDetails -> Up")
        navController.popBackStack()
    })
}