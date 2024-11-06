package com.yshmgrt.timetablespbu.ui.setup.faculty

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.yshmgrt.timetablespbu.data.setup.SetupRepository
import com.yshmgrt.timetablespbu.data.setup.University
import com.yshmgrt.timetablespbu.model.Faculty
import com.yshmgrt.timetablespbu.model.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FacultySelectViewModel @Inject constructor(
    private val setupRepository: SetupRepository
) : ViewModel() {
    val facultyList: MutableState<LoadingState<List<Faculty>>> =
        mutableStateOf(LoadingState.Loading)
    val selected: MutableState<Faculty?> = mutableStateOf(null)

    init {
        loadFacultyList()
    }

    fun loadFacultyList() {
        viewModelScope.launch(Dispatchers.IO) {
            facultyList.value = LoadingState.Loading
            val faculties = setupRepository.getFacultyList(University())
            facultyList.value = when (faculties) {
                is Either.Left ->
                    LoadingState.Error("Can't get faculty list")

                is Either.Right ->
                    LoadingState.Ready(faculties.value)

            }
        }
    }
}