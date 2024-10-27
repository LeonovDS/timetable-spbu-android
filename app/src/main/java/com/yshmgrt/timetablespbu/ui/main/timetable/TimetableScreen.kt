package com.yshmgrt.timetablespbu.ui.main.timetable

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yshmgrt.timetablespbu.R
import com.yshmgrt.timetablespbu.model.Lesson
import com.yshmgrt.timetablespbu.ui.component.SPBULogo
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.math.max

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimetableScreen(
    timetableId: Int,
    url: String,
    timetableViewModel: TimetableViewModel,
    onSettingsPressed: () -> Unit = {},
    onSelectLesson: (Lesson) -> Unit = {}
) {
    var isDatePickerOpened by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var date by remember {
        mutableStateOf(
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        )
    }
    if (isDatePickerOpened) {
        DatePickerDialog(
            onDismissRequest = { isDatePickerOpened = false },
            confirmButton = {
                Button({
                    isDatePickerOpened = false
                    if (datePickerState.selectedDateMillis != null) {
                        date =
                            Instant.fromEpochMilliseconds(
                                datePickerState.selectedDateMillis!!
                            ).toLocalDateTime(TimeZone.currentSystemDefault()).date
                    }
                }) {
                    Text(text = "Select")
                }
            },
            dismissButton = {
                OutlinedButton({
                    isDatePickerOpened = false
                }) {
                    Text(text = "Cancel")
                }
            }
        ) {
            DatePicker(datePickerState)
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
        LaunchedEffect(url, date, timetableViewModel.isOnline.value) {
            timetableViewModel.loadLessons(timetableId, url, date)
        }
        val lessons = timetableViewModel.timetable.collectAsState()
        val uiLessons = remember {
            derivedStateOf {
                lessons.value.groupBy { it.startTime.date }.flatMap {
                    listOf(TimetableItem.Date(it.key)) + it.value.map { TimetableItem.Lesson(it) }
                }
            }
        }
        val listState = rememberLazyListState()
        val position = remember {
            derivedStateOf {
                max(
                    0,
                    uiLessons.value.indexOfFirst { it is TimetableItem.Date && it.date == date }
                )
            }
        }
        LaunchedEffect(position.value) {
            listState.scrollToItem(position.value)
        }

        if (lessons.value.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No lessons")
            }
        }
        LazyColumn(state = listState, modifier = Modifier.padding(padding)) {
            items(uiLessons.value) { lesson ->
                when (lesson) {
                    is TimetableItem.Date -> DateItem(lesson.date)
                    is TimetableItem.Lesson -> LessonItem(
                        lesson.lesson,
                        onOpen = { onSelectLesson(lesson.lesson) },
                        onTemporaryBan = { timetableViewModel.temporaryBan(lesson.lesson) },
                        onPermanentBan = { timetableViewModel.permanentBan(lesson.lesson) }
                    )
                }
            }
        }
    }
}
