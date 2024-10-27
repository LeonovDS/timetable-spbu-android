package com.yshmgrt.timetablespbu.domain

import com.yshmgrt.timetablespbu.model.Lesson
import com.yshmgrt.timetablespbu.persistance.LessonDAO
import com.yshmgrt.timetablespbu.persistance.LessonData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddLessonsUC @Inject constructor(
    private val lessonDAO: LessonDAO
) {
    suspend operator fun invoke(timetableId: Int, vararg lessons: Lesson) =
        withContext(Dispatchers.IO) {
            lessons.forEach {
                lessonDAO.deleteLesson(it.name, it.startTime)
            }
            lessonDAO.insertLessons(*lessons.map {
                LessonData(
                    name = it.name,
                    startTime = it.startTime,
                    endTime = it.endTime,
                    place = it.place,
                    teacher = it.teacher,
                    isCanceled = it.isCanceled,
                    timetableId = timetableId
                )
            }.toTypedArray())
        }
}