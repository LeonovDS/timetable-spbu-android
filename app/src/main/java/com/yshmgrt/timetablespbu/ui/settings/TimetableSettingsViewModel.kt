package com.yshmgrt.timetablespbu.ui.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.yshmgrt.timetablespbu.data.timetable.TimetableIdRepository
import com.yshmgrt.timetablespbu.data.timetable.TimetableRepository
import com.yshmgrt.timetablespbu.model.LoadingState
import com.yshmgrt.timetablespbu.persistance.Timetable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimetableSettingsViewModel @Inject constructor(
    private val timetableRepository: TimetableRepository,
    private val timetableIdRepository: TimetableIdRepository,
) : ViewModel() {
    val timetableList = mutableStateOf<LoadingState<List<Timetable>>>(LoadingState.Loading)
    val currentTimetable = timetableIdRepository.timetableId.map {
        if (it is LoadingState.Ready) it.data else -1
    }.stateIn(viewModelScope, Eagerly, -1)

    init {
        loadTimetables()
    }

    fun loadTimetables() = viewModelScope.launch(Dispatchers.IO) {
        timetableList.value = LoadingState.Loading
        timetableList.value = when (val timetables = timetableRepository.getTimetable()) {
            is Either.Left -> LoadingState.Error("Can't load timetable list")
            is Either.Right -> LoadingState.Ready(timetables.value)
        }
    }

    fun deleteTimetable(timetable: Timetable) {
        viewModelScope.launch(Dispatchers.IO) {
            timetableRepository.deleteTimetable(timetable)
            loadTimetables()
        }
    }

    fun pickTimetable(timetable: Timetable) = viewModelScope.launch(Dispatchers.IO) {
        timetableIdRepository.setTimetableId(timetable.id)
    }
}