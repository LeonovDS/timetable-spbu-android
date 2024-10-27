package com.yshmgrt.timetablespbu.ui.setup.timetable

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yshmgrt.timetablespbu.ui.component.Dropdown

@ExperimentalMaterial3Api
@Composable
fun TimetableSelect(
    timetableSelectViewModel: TimetableSelectViewModel,
    url: String,
    onNext: (String) -> Unit
) {
    LaunchedEffect(true) {
        timetableSelectViewModel.getTimetables(url)
    }
    val timetables = timetableSelectViewModel.timetables.collectAsState()
    val selectedTimetable = remember { mutableStateOf(timetables.value.getOrNull(0)) }

    LaunchedEffect(timetables.value) {
        selectedTimetable.value = timetables.value.getOrNull(0)
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Dropdown(
            timetables.value,
            selectedTimetable,
            itemName = { name },
            label = { Text("Расписание") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(16.dp))
        Row(Modifier.align(Alignment.CenterHorizontally)) {
            Button(
                enabled = selectedTimetable.value != null,
                onClick = { onNext(selectedTimetable.value!!.url) }
            ) {
                Text("Далее")
            }
        }
    }
}
