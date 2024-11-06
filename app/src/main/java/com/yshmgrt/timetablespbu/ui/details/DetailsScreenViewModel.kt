package com.yshmgrt.timetablespbu.ui.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.yshmgrt.timetablespbu.data.lesson.LocalLessonRepository
import com.yshmgrt.timetablespbu.model.LoadingState
import com.yshmgrt.timetablespbu.persistance.Lesson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val localLessonRepository: LocalLessonRepository
) : ViewModel() {
    private val lessonId =
        savedStateHandle.toRoute<com.yshmgrt.timetablespbu.ui.navigation.Lesson>().lessonId
    val lesson = mutableStateOf<LoadingState<Lesson>>(LoadingState.Loading)

    init {
        loadLesson()
    }

    fun loadLesson() = viewModelScope.launch(Dispatchers.IO) {
        localLessonRepository.getLesson(lessonId).onLeft {
            lesson.value = LoadingState.Error("")
        }.onRight {
            lesson.value = LoadingState.Ready(it)
        }
    }
}
