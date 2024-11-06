package com.yshmgrt.timetablespbu.ui.setup.timetable

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import arrow.core.Either
import arrow.core.raise.either
import com.yshmgrt.timetablespbu.data.setup.Error
import com.yshmgrt.timetablespbu.data.setup.SetupRepository
import com.yshmgrt.timetablespbu.data.timetable.TimetableIdRepository
import com.yshmgrt.timetablespbu.data.timetable.TimetableIdRepositoryImpl
import com.yshmgrt.timetablespbu.data.timetable.TimetableRepository
import com.yshmgrt.timetablespbu.model.Group
import com.yshmgrt.timetablespbu.model.LoadingState
import com.yshmgrt.timetablespbu.model.TimetableDraft
import com.yshmgrt.timetablespbu.persistance.Timetable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TimetableSelectViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val setupRepository: SetupRepository,
    private val timetableRepository: TimetableRepository,
    private val timetableIdRepository: TimetableIdRepository,
) : ViewModel() {
    val group = savedStateHandle.toRoute<Group>()
    val timetableList: MutableState<LoadingState<List<Timetable>>> =
        mutableStateOf(LoadingState.Loading)
    val selectedTimetable: MutableState<Timetable?> = mutableStateOf(null)

    init {
        getTimetableList()
    }

    fun getTimetableList() {
        viewModelScope.launch(Dispatchers.IO) {
            timetableList.value = LoadingState.Loading
            val timetables = setupRepository.getTimetableList(group)
            timetableList.value = when (timetables) {
                is Either.Left -> LoadingState.Error("Can't get timetable list")
                is Either.Right -> LoadingState.Ready(timetables.value)
            }
        }
    }

    fun saveSelectedAndNavigate(timetable: Timetable, navigate: (Timetable) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val timetable = timetableRepository.insertAndGetTimetable(timetable)
            when (timetable) {
                is Either.Right -> launch(Dispatchers.Main) {
                    timetableIdRepository.setTimetableId(timetable.value.id)
                    navigate(timetable.value)
                }

                is Either.Left -> {
                    Log.e("Debug", "saveSelectedAndNavigate: Timetable not saved")
                } // TODO: Show Error toast
            }
        }
    }
}
