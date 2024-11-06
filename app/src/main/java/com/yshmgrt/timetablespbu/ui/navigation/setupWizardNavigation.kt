package com.yshmgrt.timetablespbu.ui.navigation

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.yshmgrt.timetablespbu.model.Faculty
import com.yshmgrt.timetablespbu.model.Group
import com.yshmgrt.timetablespbu.ui.setup.faculty.FacultySelect
import com.yshmgrt.timetablespbu.ui.setup.group.GroupSelect
import com.yshmgrt.timetablespbu.ui.setup.timetable.TimetableSelect
import kotlinx.serialization.Serializable

@Serializable
data object FacultySelect
typealias GroupSelect = Faculty
typealias TimetableSelect = Group

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.setupWizardNavigation(navController: NavController) =
    navigation<SetupWizard>(startDestination = FacultySelect) {
        composable<FacultySelect> {
            FacultySelect(onNext = {
                Log.d("Navigation", "Faculty -> Group($it)")
                navController.navigate(it)
            })
        }
        composable<GroupSelect> {
            GroupSelect(onNext = {
                Log.d("Navigation", "Group -> TimetableSelect($it)")
                navController.navigate(it)
            })
        }
        composable<TimetableSelect> {
            TimetableSelect(onNext = {
                Log.d("Navigation", "TimetableSelect -> Timetable($it)")
                navController.navigate(
                    Timetable(it.id)
                ) {
                    popUpTo<FacultySelect> {
                        inclusive = true
                    }
                }
            })
        }
    }