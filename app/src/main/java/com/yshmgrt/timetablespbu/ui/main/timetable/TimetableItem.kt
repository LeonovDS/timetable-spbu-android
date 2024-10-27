package com.yshmgrt.timetablespbu.ui.main.timetable

import kotlinx.datetime.LocalDate

sealed interface TimetableItem {
    data class Date(val date: LocalDate) : TimetableItem
    data class Lesson(val lesson: com.yshmgrt.timetablespbu.model.Lesson) : TimetableItem
}