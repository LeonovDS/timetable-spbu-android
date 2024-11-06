package com.yshmgrt.timetablespbu.data.lesson

import arrow.core.Either
import com.yshmgrt.timetablespbu.data.setup.Error
import com.yshmgrt.timetablespbu.persistance.Lesson
import kotlinx.datetime.LocalDateTime

interface LocalLessonRepository {
    fun getLesson(id: Long): Either<Error, Lesson>
    fun getLessonsByDate(
        timetableId: Long,
        startTime: LocalDateTime,
        endTime: LocalDateTime
    ): Either<Error, List<Lesson>>

    fun insertOrUpdate(timetableId: Long, lessons: List<Lesson>): Either<Error, List<Lesson>>
}