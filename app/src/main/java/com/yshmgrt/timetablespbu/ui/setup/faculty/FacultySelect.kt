package com.yshmgrt.timetablespbu.ui.setup.faculty

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import arrow.core.partially1
import com.yshmgrt.timetablespbu.model.Faculty
import com.yshmgrt.timetablespbu.model.LoadingState
import com.yshmgrt.timetablespbu.ui.component.Dropdown
import com.yshmgrt.timetablespbu.ui.setup.SetupScreen

@ExperimentalMaterial3Api
@Composable
fun FacultySelect(
    viewModel: FacultySelectViewModel = hiltViewModel(),
    onNext: (Faculty) -> Unit = {},
) {
    val facultiesState = viewModel.facultyList.value
    val faculties = if (facultiesState is LoadingState.Ready) facultiesState.data else listOf()
    val isLoading = facultiesState is LoadingState.Loading
    val selected = viewModel.selected
    SetupScreen(
        isLoading = isLoading,
        onNext = selected.value?.let<Faculty, () -> Unit> { onNext.partially1(it) }) {
        Box(contentAlignment = Alignment.Center) {
            Dropdown<Faculty>(faculties, selected, itemName = {
                faculty
            }, label = { Text("Факультет") }, modifier = Modifier.fillMaxWidth())
        }

    }
}

