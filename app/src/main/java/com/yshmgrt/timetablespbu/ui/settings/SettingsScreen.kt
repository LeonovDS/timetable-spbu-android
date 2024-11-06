package com.yshmgrt.timetablespbu.ui.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    isLoading: Boolean = false,
    onGoBack: () -> Unit = {},
    content: @Composable (modifier: Modifier) -> Unit
) {
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            content(Modifier.fillMaxSize())
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(48.dp))
            }
        }
    }
}

@Composable
fun Settings(
    onBackPressed: () -> Unit = {},
    onSetup: () -> Unit = {},
    onOpenTimetableSettings: () -> Unit = {},
    onOpenBanSettings: () -> Unit = {},
) {
    SettingsScreen(onGoBack = onBackPressed) { modifier ->
        Column(modifier = modifier) {
            SettingsRow("Open new Timetable", "Open new Timetable") {
                onSetup()
            }
            SettingsRow("Timetable", "Select timetable") {
                onOpenTimetableSettings()
            }
            SettingsRow("Hidden lessons", "Show hidden lessons") {
                onOpenBanSettings()
            }
        }
    }
}