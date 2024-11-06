package com.yshmgrt.timetablespbu.ui.setup.group

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import arrow.core.Either
import com.yshmgrt.timetablespbu.data.setup.SetupRepository
import com.yshmgrt.timetablespbu.model.Faculty
import com.yshmgrt.timetablespbu.model.Group
import com.yshmgrt.timetablespbu.model.LoadingState
import com.yshmgrt.timetablespbu.model.Programme
import com.yshmgrt.timetablespbu.model.ProgrammeGroup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupSelectViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val setupRepository: SetupRepository,
) : ViewModel() {
    val faculty = savedStateHandle.toRoute<Faculty>()
    val programmeGroupList: MutableState<LoadingState<List<ProgrammeGroup>>> =
        mutableStateOf(LoadingState.Loading)
    val selectedProgrammeGroup: MutableState<ProgrammeGroup?> = mutableStateOf(null)
    val selectedProgramme: MutableState<Programme?> = mutableStateOf(null)
    val selectedGroup: MutableState<Group?> = mutableStateOf(null)

    init {
        updateGroups()
    }

    fun updateGroups() {
        viewModelScope.launch(Dispatchers.IO) {
            programmeGroupList.value = LoadingState.Loading
            val programmeGroups = setupRepository.getGroupList(faculty)
            programmeGroupList.value = when (programmeGroups) {
                is Either.Left -> LoadingState.Error("Can't get group list")
                is Either.Right ->
                    LoadingState.Ready(programmeGroups.value)

            }
        }
    }
}