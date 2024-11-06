package com.yshmgrt.timetablespbu.data.lesson

import arrow.core.Either
import com.yshmgrt.timetablespbu.data.setup.Error
import com.yshmgrt.timetablespbu.persistance.Lesson
import com.yshmgrt.timetablespbu.persistance.Timetable
import kotlinx.datetime.LocalDate

interface RemoteLessonRepository {
    suspend fun getLessonList(timetable: Timetable, date: LocalDate): Either<Error, List<Lesson>>
}
