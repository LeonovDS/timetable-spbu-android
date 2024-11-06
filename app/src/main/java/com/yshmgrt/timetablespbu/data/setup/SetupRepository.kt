package com.yshmgrt.timetablespbu.data.setup

import arrow.core.Either
import com.yshmgrt.timetablespbu.model.Faculty
import com.yshmgrt.timetablespbu.model.Group
import com.yshmgrt.timetablespbu.model.ProgrammeGroup
import com.yshmgrt.timetablespbu.model.TimetableDraft
import com.yshmgrt.timetablespbu.persistance.Timetable

sealed interface Error {
    data class UnknownException(val exception: Throwable) : Error {
        override fun toString(): String {
            return "UnknownException: $exception"
        }
    }
}

interface SetupRepository {
    suspend fun getFacultyList(university: University): Either<Error, List<Faculty>>
    suspend fun getGroupList(faculty: Faculty): Either<Error, List<ProgrammeGroup>>
    suspend fun getTimetableList(group: Group): Either<Error, List<Timetable>>
}
