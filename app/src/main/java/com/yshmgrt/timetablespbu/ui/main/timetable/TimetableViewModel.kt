package com.yshmgrt.timetablespbu.ui.main.timetable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yshmgrt.timetablespbu.data.RemoteTimetableRepository
import com.yshmgrt.timetablespbu.domain.AddLessonsUC
import com.yshmgrt.timetablespbu.domain.LoadLessonsUC
import com.yshmgrt.timetablespbu.domain.BanUC
import com.yshmgrt.timetablespbu.domain.InternetController
import com.yshmgrt.timetablespbu.model.Lesson
import com.yshmgrt.timetablespbu.util.endOfDay
import com.yshmgrt.timetablespbu.util.endOfWeek
import com.yshmgrt.timetablespbu.util.startOfDay
import com.yshmgrt.timetablespbu.util.startOfWeek
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

@HiltViewModel
class TimetableViewModel @Inject constructor(
    private val remoteTimetableRepository: RemoteTimetableRepository,
    private val addLessonsUC: AddLessonsUC,
    private val loadLessonsUC: LoadLessonsUC,
    private val bansUC: BanUC,
    private val internetController: InternetController,
) :
    ViewModel() {
    private val _timetable = MutableStateFlow<List<Lesson>>(emptyList())
    val timetable = _timetable.asStateFlow()
    val isOnline = internetController.isOnline

    fun loadLessons(
        timetableId: Int = 0,
        url: String? = null,
        date: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    ) {
        viewModelScope.launch {
            if (url != null && internetController.isOnline.value) {
                val timetable = remoteTimetableRepository.getTimetable(url, date).toTypedArray()
                addLessonsUC(timetableId, *timetable)
            }
            val start = date.startOfWeek().startOfDay()
            val end = date.endOfWeek().endOfDay()
            _timetable.value = loadLessonsUC(timetableId, start, end)
        }
    }

    fun permanentBan(lesson: Lesson) {
        viewModelScope.launch {
            bansUC(lesson, true)
            loadLessons()
        }
    }

    fun temporaryBan(lesson: Lesson) {
        viewModelScope.launch {
            bansUC(lesson, false)
            loadLessons()
        }
    }
}
