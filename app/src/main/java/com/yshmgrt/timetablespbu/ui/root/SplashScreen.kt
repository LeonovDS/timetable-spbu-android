package com.yshmgrt.timetablespbu.ui.root

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.yshmgrt.timetablespbu.ui.component.SPBULogo
import kotlinx.coroutines.flow.first

@Composable
fun SplashScreen(rootViewModel: RootViewModel, doSetup: () -> Unit, doStart: () -> Unit) {
    LaunchedEffect(true) {
        if (rootViewModel.hasUrl.first()) {
            doStart()
        } else {
            doSetup()
        }
    }
    Scaffold(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(SPBULogo, contentDescription = null)
        }
    }
}
