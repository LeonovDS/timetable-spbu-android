package com.yshmgrt.timetablespbu.ui.timetable

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yshmgrt.timetablespbu.model.LoadingState
import com.yshmgrt.timetablespbu.persistance.Lesson
import kotlinx.coroutines.FlowPreview
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.math.max

@FlowPreview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimetableScreen(
    viewModel: TimetableViewModel = hiltViewModel(),
    onSettingsPressed: () -> Unit = {},
    onSelectLesson: (Lesson) -> Unit = {}
) {
    var isDatePickerOpened by remember { mutableStateOf(false) }

    if (isDatePickerOpened) {
        DatePicker {
            Log.d("TimetableScreen", "Date: ${it ?: "No date"}")
            isDatePickerOpened = false
            it?.let {
                viewModel.setDate(it)
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Timetable") },
                actions = {
                    IconButton(onClick = {
                        isDatePickerOpened = true
                    }) {
                        Icon(
                            Icons.Outlined.DateRange,
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = onSettingsPressed) {
                        Icon(
                            Icons.Outlined.Settings,
                            contentDescription = null
                        )
                    }
                }
            )
        },
    ) { padding ->
        when (val timetable = viewModel.timetable.value) {
            is LoadingState.Error -> {
                Column(Modifier.padding(padding)) {
                    Text("Error: ${timetable.message}")
                    Button({
                        viewModel.loadLessons()
                    }) {
                        Text("Retry")
                    }
                }
            }

            LoadingState.Loading -> {
                Box(Modifier.padding(padding), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp)
                    )
                }
            }

            is LoadingState.Ready -> {
                TimetableView(
                    timetable.data, viewModel, onSelectLesson,
                    Modifier.padding(padding)
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
private fun TimetableView(
    lessons: List<Lesson>,
    viewModel: TimetableViewModel,
    onSelectLesson: (Lesson) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiLessons = lessons.groupBy { it.startTime.date }.flatMap {
        listOf(TimetableItem.Date(it.key)) + it.value.map { TimetableItem.Lesson(it) }
    }
    val listState = rememberLazyListState()
    val position = remember {
        derivedStateOf {
            Log.d("TimetableScreen", "When calculating position")
            max(
                0,
                uiLessons.indexOfFirst { it is TimetableItem.Date && it.date == viewModel.date.value }
            )
        }
    }
    LaunchedEffect(position.value) {
        listState.scrollToItem(position.value)
    }

    if (lessons.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No lessons")
        }
    }
    LazyColumn(state = listState, modifier = modifier) {
        items(uiLessons) { lesson ->
            when (lesson) {
                is TimetableItem.Date -> DateItem(lesson.date)
                is TimetableItem.Lesson -> LessonItem(
                    lesson.lesson,
                    onOpen = { onSelectLesson(lesson.lesson) },
                    onTemporaryBan = { viewModel.temporaryBan(lesson.lesson) },
                    onPermanentBan = { viewModel.permanentBan(lesson.lesson) }
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun DatePicker(
    closeDialog: (LocalDate?) -> Unit
) {
    val datePickerState = rememberDatePickerState()
    DatePickerDialog(
        onDismissRequest = { closeDialog(null) },
        confirmButton = {
            Button({
                closeDialog(
                    datePickerState.selectedDateMillis?.let { Instant.fromEpochMilliseconds(it) }
                        ?.toLocalDateTime(
                            TimeZone.currentSystemDefault()
                        )?.date
                )
            }) {
                Text(text = "Select")
            }
        },
        dismissButton = {
            OutlinedButton({ closeDialog(null) }) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(datePickerState)
    }
}
