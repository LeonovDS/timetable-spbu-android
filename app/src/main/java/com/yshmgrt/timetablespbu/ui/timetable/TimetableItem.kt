package com.yshmgrt.timetablespbu.ui.timetable

import kotlinx.datetime.LocalDate

sealed interface TimetableItem {
    data class Date(val date: LocalDate) : TimetableItem
    data class Lesson(val lesson: com.yshmgrt.timetablespbu.persistance.Lesson) : TimetableItem
}