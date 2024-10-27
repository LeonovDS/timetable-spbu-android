package com.yshmgrt.timetablespbu.ui.setup.group

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
import com.yshmgrt.timetablespbu.model.Group
import com.yshmgrt.timetablespbu.model.Programme
import com.yshmgrt.timetablespbu.model.ProgrammeGroup
import com.yshmgrt.timetablespbu.model.TimetableSummary
import com.yshmgrt.timetablespbu.ui.component.Dropdown

@ExperimentalMaterial3Api
@Composable
fun GroupSelect(
    groupSelectViewModel: GroupSelectViewModel,
    url: String,
    onNext: (TimetableSummary) -> Unit
) {
    LaunchedEffect(true) {
        groupSelectViewModel.getProgrammeGroups(url)
    }
    val groups = groupSelectViewModel.programmeGroups.collectAsState()
    val selectedProgrammeGroup: MutableState<ProgrammeGroup?> = remember { mutableStateOf(null) }
    val selectedProgramme: MutableState<Programme?> = remember { mutableStateOf(null) }
    val selectedGroup: MutableState<Group?> = remember { mutableStateOf(null) }

    LaunchedEffect(groups.value) {
        selectedProgrammeGroup.value = groups.value.getOrNull(0)
    }
    LaunchedEffect(selectedProgrammeGroup.value) {
        selectedProgramme.value = selectedProgrammeGroup.value?.programmes?.getOrNull(0)
    }
    LaunchedEffect(selectedProgramme.value) {
        selectedGroup.value = selectedProgramme.value?.groups?.getOrNull(0)
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Dropdown(
            groups.value,
            selectedProgrammeGroup,
            itemName = { name },
            label = { Text("Тип программы") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
        Dropdown(
            selectedProgrammeGroup.value?.programmes ?: emptyList(),
            selectedProgramme,
            itemName = { name },
            label = { Text("Программа") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
        Dropdown(
            selectedProgramme.value?.groups ?: emptyList(),
            selectedGroup,
            itemName = { "$name ($year)" },
            label = { Text("Группа") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        Row(Modifier.align(Alignment.CenterHorizontally)) {
            Button(enabled = selectedGroup.value != null, onClick = {
                onNext(
                    TimetableSummary(
                        selectedProgramme.value?.name ?: "",
                        selectedGroup.value?.name ?: "",
                        selectedGroup.value?.year?.toInt() ?: 0,
                        (Configuration.BASE_URL + selectedGroup.value?.url),
                    )
                )
            }) {
                Text("Далее")
            }
        }
    }
}