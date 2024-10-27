package com.yshmgrt.timetablespbu.ui.setup.faculty

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yshmgrt.timetablespbu.Configuration
import com.yshmgrt.timetablespbu.model.Faculty
import com.yshmgrt.timetablespbu.model.TimetableSummary
import com.yshmgrt.timetablespbu.ui.component.Dropdown

@ExperimentalMaterial3Api
@Composable
fun FacultySelect(
    facultySelectViewModel: FacultySelectViewModel,
    onNext: (String) -> Unit
) {
    LaunchedEffect(true) {
        facultySelectViewModel.getFaculties()
    }
    val faculties = facultySelectViewModel.faculties.collectAsState()
    val selected: MutableState<Faculty?> =
        remember { mutableStateOf(faculties.value.firstOrNull()) }
    LaunchedEffect(faculties.value) {
        selected.value = faculties.value.firstOrNull()
    }
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Dropdown(faculties.value, selected, itemName = {
            name
        }, label = { Text("Факультет") }, modifier = Modifier.fillMaxWidth())
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        Row(Modifier.align(Alignment.CenterHorizontally)) {
            Button(
                enabled = selected.value != null,
                onClick = {
                    onNext(Configuration.BASE_URL + selected.value!!.url)
                },
            ) {
                Text("Далее")
            }
        }
    }
}