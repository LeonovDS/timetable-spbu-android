package com.yshmgrt.timetablespbu.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import arrow.core.Option
import com.yshmgrt.timetablespbu.data.timetable.TimetableIdRepository
import com.yshmgrt.timetablespbu.model.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    private val timetableIdRepository: TimetableIdRepository
) : ViewModel() {
    val timetableId: State<LoadingState<Long>>
        @Composable get() = timetableIdRepository.timetableId.collectAsState(
            LoadingState.Loading
        )
}