package com.yshmgrt.timetablespbu.ui.timetable

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.yshmgrt.timetablespbu.data.ban.BanRepository
import com.yshmgrt.timetablespbu.data.timetable.TimetableIdRepository
import com.yshmgrt.timetablespbu.domain.CachingLessonLoader
import com.yshmgrt.timetablespbu.model.LoadingState
import com.yshmgrt.timetablespbu.persistance.Ban
import com.yshmgrt.timetablespbu.persistance.Lesson
import com.yshmgrt.timetablespbu.util.startOfDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.timeout
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@FlowPreview
@HiltViewModel
class TimetableViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    timetableIdRepository: TimetableIdRepository,
    private val cachingLessonLoader: CachingLessonLoader,
    private val banRepository: BanRepository,
) : ViewModel() {
    private val timetableIdState =
        timetableIdRepository.timetableId.stateIn(viewModelScope, Eagerly, LoadingState.Loading)
    val timetable: MutableState<LoadingState<List<Lesson>>> = mutableStateOf(LoadingState.Loading)
    val date: MutableState<LocalDate> =
        mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date)
    var loadingJob: Job? = null

    companion object {
        const val TAG = "TimetableViewModel"
    }

    init {
        loadLessons()
        viewModelScope.launch(Dispatchers.IO) {
            timetableIdState.onEach {
                loadLessons()
            }.collect()
        }
    }

    fun setDate(newDate: LocalDate) {
        date.value = newDate
        loadLessons()
    }

    fun loadLessons() {
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch(Dispatchers.IO) {
            val timetableId =
                (timetableIdState.filter { it is LoadingState.Ready }.timeout(5000.seconds)
                    .first() as LoadingState.Ready).data
            cachingLessonLoader.loadLessons(timetableId, date.value.startOfDay()).collect {
                Log.d(TAG, "loadLessons: $it")
                timetable.value = it
            }
        }
    }

    fun permanentBan(lesson: Lesson) {
        viewModelScope.launch(Dispatchers.IO) {
            ban(lesson, true)
            loadLessons()
        }
    }

    fun temporaryBan(lesson: Lesson) {
        viewModelScope.launch(Dispatchers.IO) {
            ban(lesson, false)
            loadLessons()
        }
    }

    private fun ban(lesson: Lesson, isPermanent: Boolean) {
        banRepository.insertBan(
            Ban(
                name = lesson.name,
                startTime = lesson.startTime,
                endTime = lesson.endTime,
                isPermanent = isPermanent
            )
        )
    }
}
