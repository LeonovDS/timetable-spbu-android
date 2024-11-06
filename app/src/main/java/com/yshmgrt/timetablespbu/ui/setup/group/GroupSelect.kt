package com.yshmgrt.timetablespbu.ui.setup.group

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.partially1
import com.yshmgrt.timetablespbu.model.Group
import com.yshmgrt.timetablespbu.model.LoadingState
import com.yshmgrt.timetablespbu.model.Programme
import com.yshmgrt.timetablespbu.model.ProgrammeGroup
import com.yshmgrt.timetablespbu.ui.component.Dropdown
import com.yshmgrt.timetablespbu.ui.setup.SetupScreen

@ExperimentalMaterial3Api
@Composable
fun GroupSelect(
    groupSelectViewModel: GroupSelectViewModel = hiltViewModel(), onNext: (Group) -> Unit
) {
    val groupsState = groupSelectViewModel.programmeGroupList.value
    val isLoading = groupsState is LoadingState.Loading
    val programmeGroups = if (groupsState is LoadingState.Ready) groupsState.data else emptyList()
    val programmes = groupSelectViewModel.selectedProgrammeGroup.value?.programmes
        ?: emptyList<Programme>()
    val groups = groupSelectViewModel.selectedProgramme.value?.groups ?: emptyList<Group>()
    SetupScreen(
        isLoading = isLoading,
        onNext = groupSelectViewModel.selectedGroup.value?.let<Group, () -> Unit> {
            onNext.partially1(
                it
            )
        }) {
        Dropdown<ProgrammeGroup>(
            programmeGroups,
            groupSelectViewModel.selectedProgrammeGroup,
            itemName = { name },
            label = { Text("Тип программы") },
            modifier = Modifier.fillMaxWidth()
        )
        Dropdown<Programme>(
            programmes,
            groupSelectViewModel.selectedProgramme,
            itemName = { name },
            label = { Text("Программа") },
            modifier = Modifier.fillMaxWidth()
        )
        Dropdown<Group>(
            groups,
            groupSelectViewModel.selectedGroup,
            itemName = { "$group (${year})" },
            label = { Text("Группа") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

