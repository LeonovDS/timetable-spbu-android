package com.yshmgrt.timetablespbu.ui.setup.faculty

import androidx.lifecycle.ViewModel
import com.yshmgrt.timetablespbu.data.SetupRepository
import com.yshmgrt.timetablespbu.model.Faculty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FacultySelectViewModel @Inject constructor(private val setupRepository: SetupRepository) :
    ViewModel() {
    private val _faculties: MutableStateFlow<List<Faculty>> = MutableStateFlow(emptyList())
    val faculties = _faculties.asStateFlow()

    suspend fun getFaculties() {
        _faculties.value = setupRepository.getFacultyList()
    }
}