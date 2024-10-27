package com.yshmgrt.timetablespbu.ui.main.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onSetup: () -> Unit, onGoBack: () -> Unit = {}) {
    val navController = rememberNavController()
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Settings") }, navigationIcon = {
            IconButton(onClick = {
                if (navController.previousBackStackEntry != null) {
                    navController.navigateUp()
                } else {
                    onGoBack()
                }
            }) {
                Icon(
                    Icons.AutoMirrored.Outlined.ArrowBack, null
                )
            }
        })
    }) { padding ->
        NavHost(navController, startDestination = SettingsDestination.Main) {
            composable<SettingsDestination.Main> {
                Column(modifier = Modifier.padding(padding)) {
                    SettingsRow("Open new Timetable", "Open new Timetable") {
                        onSetup()
                    }
                    SettingsRow("Timetable", "Select timetable") {
                        navController.navigate(SettingsDestination.Timetable)
                    }
                    SettingsRow("Hidden lessons", "Show hidden lessons") {
                        navController.navigate(SettingsDestination.Ban)
                    }
                }
            }
            composable<SettingsDestination.Timetable> {
                val timetableSettingsViewModel = hiltViewModel<TimetableSettingsViewModel>()
                TimetableScreen(timetableSettingsViewModel, modifier = Modifier.padding(padding))
            }
            composable<SettingsDestination.Ban> {
                val banViewModel = hiltViewModel<BanViewModel>()
                BanScreen(banViewModel, modifier = Modifier.padding(padding))
            }
        }
    }
}
