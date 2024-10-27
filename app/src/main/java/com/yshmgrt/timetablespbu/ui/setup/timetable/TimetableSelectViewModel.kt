package com.yshmgrt.timetablespbu.ui.setup.timetable

import androidx.lifecycle.ViewModel
import com.yshmgrt.timetablespbu.data.SetupRepositoryImpl
import com.yshmgrt.timetablespbu.model.Timetable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TimetableSelectViewModel @Inject constructor() : ViewModel() {
    private val _timetables: MutableStateFlow<List<Timetable>> = MutableStateFlow(emptyList())
    val timetables = _timetables.asStateFlow()

    suspend fun getTimetables(url: String) {
        _timetables.value = SetupRepositoryImpl().getTimetableList(url)
    }
}