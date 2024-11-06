package com.yshmgrt.timetablespbu.data.timetable

import arrow.core.Either
import com.yshmgrt.timetablespbu.data.setup.Error
import com.yshmgrt.timetablespbu.model.TimetableDraft
import com.yshmgrt.timetablespbu.persistance.Timetable

interface TimetableRepository {
    fun getTimetable(): Either<Error, List<Timetable>>
    fun getTimetableById(id: Long): Either<Error, Timetable>
    fun insertAndGetTimetable(timetable: Timetable): Either<Error, Timetable>
    fun deleteTimetable(timetable: Timetable): Either<Error, Unit>
}
