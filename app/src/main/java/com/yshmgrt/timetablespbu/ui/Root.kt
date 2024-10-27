package com.yshmgrt.timetablespbu.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yshmgrt.timetablespbu.ui.root.RootDestination
import com.yshmgrt.timetablespbu.ui.root.RootViewModel
import com.yshmgrt.timetablespbu.ui.root.SplashScreen
import com.yshmgrt.timetablespbu.ui.setup.SetupScreen
import com.yshmgrt.timetablespbu.ui.main.MainScreen

@ExperimentalMaterial3Api
@Composable
fun Root() {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<RootViewModel>()

    Surface(modifier = Modifier.fillMaxSize()) {
        NavHost(navController, startDestination = RootDestination.Splash) {
            composable<RootDestination.Splash> {
                SplashScreen(viewModel, doSetup = {
                    navController.navigate(RootDestination.Setup)
                }, doStart = {
                    navController.navigate(RootDestination.Timetable)
                })
            }
            composable<RootDestination.Setup> {
                SetupScreen(onNext = {
                    viewModel.saveTimetable(it)
                    navController.navigate(RootDestination.Timetable)
                })
            }
            composable<RootDestination.Timetable> {
                MainScreen(viewModel.url.value, onSetup = {
                    navController.navigate(RootDestination.Setup)
                })
            }
        }
    }
}
