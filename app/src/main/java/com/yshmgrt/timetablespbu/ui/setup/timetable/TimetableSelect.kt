package com.yshmgrt.timetablespbu.ui.setup.timetable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import arrow.core.partially1
import com.yshmgrt.timetablespbu.model.LoadingState
import com.yshmgrt.timetablespbu.persistance.Timetable
import com.yshmgrt.timetablespbu.ui.component.Dropdown
import com.yshmgrt.timetablespbu.ui.setup.SetupScreen

@ExperimentalMaterial3Api
@Composable
fun TimetableSelect(
    viewModel: TimetableSelectViewModel = hiltViewModel(),
    onNext: (Timetable) -> Unit
) {
    val timetableState = viewModel.timetableList.value
    val selected = viewModel.selectedTimetable
    val timetables = if (timetableState is LoadingState.Ready) timetableState.data else emptyList()
    val isLoading = timetableState is LoadingState.Loading

    SetupScreen(isLoading = isLoading, onNext = selected.value?.let<Timetable, () -> Unit> {
        viewModel::saveSelectedAndNavigate.partially1<Timetable, (Timetable) -> Unit, Unit>(
            it
        ).partially1(onNext)
    }) {
        Dropdown<Timetable>(
            timetables,
            selected,
            itemName = { timetable },
            label = { Text("Расписание") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
