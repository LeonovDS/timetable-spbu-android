package com.yshmgrt.timetablespbu.ui.main.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yshmgrt.timetablespbu.domain.GetTimetableUC
import com.yshmgrt.timetablespbu.domain.PickTimetableUC
import com.yshmgrt.timetablespbu.model.TimetableSummary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimetableSettingsViewModel @Inject constructor(
    private val getTimetableUC: GetTimetableUC,
    private val pickTimetableUC: PickTimetableUC,
) : ViewModel() {
    private val _timetable = MutableStateFlow<List<TimetableSummary>>(emptyList())
    val timetable = _timetable.asStateFlow()

    fun loadTimetables() = viewModelScope.launch {
        _timetable.value = getTimetableUC()
    }

    fun pickTimetable(timetable: TimetableSummary) = viewModelScope.launch {
        pickTimetableUC(timetable)
    }
}