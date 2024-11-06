package com.yshmgrt.timetablespbu.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import arrow.core.None
import arrow.core.Some
import com.yshmgrt.timetablespbu.model.LoadingState
import com.yshmgrt.timetablespbu.ui.navigation.Navigation
import com.yshmgrt.timetablespbu.ui.navigation.SetupWizard
import com.yshmgrt.timetablespbu.ui.navigation.Timetable

@ExperimentalMaterial3Api
@Composable
fun Root(viewModel: RootViewModel = hiltViewModel()) {
    Surface(modifier = Modifier.fillMaxSize()) {
        val idLoadingState = viewModel.timetableId.value
        LaunchedEffect(idLoadingState) {
            Log.d("Debug", "Timetable ID: $idLoadingState")
        }
        when (idLoadingState) {
            LoadingState.Loading -> Box(Modifier) {
                CircularProgressIndicator()
            }

            is LoadingState.Ready -> {
                Navigation(Timetable(idLoadingState.data))
            }

            is LoadingState.Error ->
                Navigation(SetupWizard)
        }
    }
}
