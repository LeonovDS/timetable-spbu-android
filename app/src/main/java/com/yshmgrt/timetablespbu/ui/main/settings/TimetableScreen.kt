package com.yshmgrt.timetablespbu.ui.main.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TimetableScreen(
    timetableSettingsViewModel: TimetableSettingsViewModel,
    modifier: Modifier = Modifier,
) {
    val timetables = timetableSettingsViewModel.timetable.collectAsState()
    LaunchedEffect(true) {
        timetableSettingsViewModel.loadTimetables()
    }
    if (timetables.value.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Пусто")
        }
    }
    LazyColumn(modifier = modifier) {
        items(timetables.value) {
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(it.programme)
                    Text(it.group)
                    Text(it.year.toString())
                    TextButton({
                        timetableSettingsViewModel.pickTimetable(it)
                    }) {
                        Text("Pick")
                    }
                }
            }
        }
    }
}

