package com.yshmgrt.timetablespbu.ui.setup.group

import androidx.lifecycle.ViewModel
import com.yshmgrt.timetablespbu.data.SetupRepository
import com.yshmgrt.timetablespbu.model.ProgrammeGroup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GroupSelectViewModel @Inject constructor(private val setupRepository: SetupRepository) :
    ViewModel() {
    private val _programmeGroups: MutableStateFlow<List<ProgrammeGroup>> =
        MutableStateFlow(emptyList())
    val programmeGroups = _programmeGroups.asStateFlow()

    suspend fun getProgrammeGroups(url: String) {
        _programmeGroups.value = setupRepository.getGroupList(url)
    }
}