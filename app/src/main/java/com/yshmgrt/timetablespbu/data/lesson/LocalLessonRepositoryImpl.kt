package com.yshmgrt.timetablespbu.data.lesson

import android.util.Log
import arrow.core.Either
import com.yshmgrt.timetablespbu.data.setup.Error
import com.yshmgrt.timetablespbu.persistance.LessonDAO
import com.yshmgrt.timetablespbu.persistance.Lesson
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject

class LocalLessonRepositoryImpl @Inject constructor(
    private val lessonDAO: LessonDAO,
) : LocalLessonRepository {
    override fun getLesson(id: Long): Either<Error, Lesson> = Either.catch {
        lessonDAO.getLesson(id)
    }.mapLeft {
        Log.e(TAG, "getLesson: error: $it")
        Error.UnknownException(it)
    }

    override fun getLessonsByDate(
        timetableId: Long,
        startTime: LocalDateTime,
        endTime: LocalDateTime
    ): Either<Error, List<Lesson>> = Either.catch {
        lessonDAO.getLessonsByDates(timetableId, startTime, endTime)
    }.mapLeft {
        Log.e(TAG, "getLessonsByDate: error: $it")
        Error.UnknownException(it)
    }

    override fun insertOrUpdate(
        timetableId: Long,
        lessons: List<Lesson>
    ): Either<Error, List<Lesson>> = Either.catch {
        lessonDAO.updateLessons(timetableId, lessons)
    }.mapLeft {
        Log.e(TAG, "insertOrUpdate: error: $it")
        Error.UnknownException(it)
    }

    companion object {
        const val TAG = "LocalLessonRepository"
    }
}