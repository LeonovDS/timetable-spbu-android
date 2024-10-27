package com.yshmgrt.timetablespbu.data

import com.yshmgrt.timetablespbu.model.Lesson
import kotlinx.datetime.LocalDate

interface RemoteTimetableRepository {
    suspend fun getTimetable(url: String, date: LocalDate): List<Lesson>
}
