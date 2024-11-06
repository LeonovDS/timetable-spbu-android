package com.yshmgrt.timetablespbu.data.timetable

import android.util.Log
import arrow.core.Either
import com.yshmgrt.timetablespbu.data.setup.Error
import com.yshmgrt.timetablespbu.model.TimetableDraft
import com.yshmgrt.timetablespbu.persistance.Timetable
import com.yshmgrt.timetablespbu.persistance.TimetableDAO
import javax.inject.Inject

class TimetableRepositoryImpl @Inject constructor(private val timetableDAO: TimetableDAO) :
    TimetableRepository {
    override fun getTimetable(): Either<Error, List<Timetable>> = Either.Companion.catch {
        timetableDAO.getTimetables()
    }.mapLeft {
        Log.e(TAG, "getTimetable: error: $it")
        Error.UnknownException(it)
    }

    override fun insertAndGetTimetable(timetable: Timetable): Either<Error, Timetable> =
        Either.Companion.catch {
            timetableDAO.insertAndGetTimetable(timetable)
        }.mapLeft {
            Log.e(TAG, "insertAndGetTimetable: error: $it")
            Error.UnknownException(it)
        }

    override fun deleteTimetable(timetable: Timetable): Either<Error, Unit> =
        Either.Companion.catch {
            timetableDAO.deleteTimetable(timetable)
        }.mapLeft {
            Log.e(TAG, "deleteTimetable: error: $it")
            Error.UnknownException(it)
        }

    override fun getTimetableById(id: Long): Either<Error, Timetable> = Either.Companion.catch {
        timetableDAO.getTimetableById(id)
    }.mapLeft {
        Log.e(TAG, "getTimetableById: error: $it")
        Error.UnknownException(it)
    }

    companion object {
        const val TAG = "TimetableRepository"
    }
}