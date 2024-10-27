package com.yshmgrt.timetablespbu.ui.setup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.yshmgrt.timetablespbu.Configuration
import com.yshmgrt.timetablespbu.model.TimetableSummary
import com.yshmgrt.timetablespbu.ui.component.SPBULogo
import com.yshmgrt.timetablespbu.ui.setup.faculty.FacultySelect
import com.yshmgrt.timetablespbu.ui.setup.faculty.FacultySelectViewModel
import com.yshmgrt.timetablespbu.ui.setup.group.GroupSelect
import com.yshmgrt.timetablespbu.ui.setup.group.GroupSelectViewModel
import com.yshmgrt.timetablespbu.ui.setup.timetable.TimetableSelect
import com.yshmgrt.timetablespbu.ui.setup.timetable.TimetableSelectViewModel

@ExperimentalMaterial3Api
@Composable
fun SetupScreen(onNext: (TimetableSummary) -> Unit) {
    val navController = rememberNavController()
    var timetableSummary by remember {
        mutableStateOf(TimetableSummary("", "", 0, ""))
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Timetable") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Icon(SPBULogo, contentDescription = "")
            NavHost(navController, startDestination = SetupDestination.FacultySelect) {
                composable<SetupDestination.FacultySelect> {
                    val viewModel = hiltViewModel<FacultySelectViewModel>()
                    FacultySelect(
                        viewModel,
                        onNext = {
                            timetableSummary = timetableSummary.copy(url = it)
                            navController.navigate(SetupDestination.GroupSelect(url = it))
                        }
                    )
                }
                composable<SetupDestination.GroupSelect> {
                    val viewModel = hiltViewModel<GroupSelectViewModel>()
                    val url = it.toRoute<SetupDestination.GroupSelect>().url
                    GroupSelect(
                        viewModel,
                        url,
                        onNext = {
                            timetableSummary = timetableSummary.copy(
                                programme = it.programme,
                                group = it.group,
                                year = it.year,
                                url = it.url
                            )
                            navController.navigate(SetupDestination.TimetableSelect(url = it.url))
                        }
                    )
                }
                composable<SetupDestination.TimetableSelect> {
                    val viewModel = hiltViewModel<TimetableSelectViewModel>()
                    val url = it.toRoute<SetupDestination.TimetableSelect>().url
                    TimetableSelect(
                        viewModel,
                        url,
                        onNext = {
                            onNext(timetableSummary.copy(url = Configuration.BASE_URL + it))
                        }
                    )
                }
            }
        }
    }
}