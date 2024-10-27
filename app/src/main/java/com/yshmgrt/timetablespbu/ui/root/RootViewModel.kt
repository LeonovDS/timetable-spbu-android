package com.yshmgrt.timetablespbu.ui.root

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yshmgrt.timetablespbu.data.UrlRepository
import com.yshmgrt.timetablespbu.domain.SaveTimetableUC
import com.yshmgrt.timetablespbu.model.TimetableSummary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    private val urlRepository: UrlRepository,
    private val saveTimetableUC: SaveTimetableUC
) : ViewModel() {
    fun saveTimetable(timetableSummary: TimetableSummary) = viewModelScope.launch {
        Log.d("RootViewModel", "saveTimetable: $timetableSummary")
        saveTimetableUC(timetableSummary)
        Log.d("RootViewModel", "----- saveTimetable: $timetableSummary")
        urlRepository.setUrl(timetableSummary.url)
    }

    val hasUrl = urlRepository.url.map {
        it != null
    }

    val url = urlRepository.url.stateIn(viewModelScope, started = SharingStarted.Eagerly, "")
}