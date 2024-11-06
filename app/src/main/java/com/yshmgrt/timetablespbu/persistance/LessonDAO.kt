package com.yshmgrt.timetablespbu.persistance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.datetime.LocalDateTime

@Dao
interface LessonDAO {
    @Insert
    fun insertLessons(lessons: List<Lesson>)

    @Query("SELECT * FROM Lesson WHERE id = :id")
    fun getLesson(id: Long): Lesson

    @Query(
        "SELECT * FROM Lesson " +
                "WHERE timetable_id = :timetableId " +
                "AND start_time >= :startTime " +
                "AND end_time <= :endTime"
    )
    fun getLessonsByDates(
        timetableId: Long,
        startTime: LocalDateTime,
        endTime: LocalDateTime
    ): List<Lesson>


    @Query(
        "DELETE FROM Lesson " +
                "WHERE timetable_id = :timetableId " +
                "AND start_time >= :startTime " +
                "AND end_time <= :endTime"
    )
    fun deleteLessonByDates(timetableId: Long, startTime: LocalDateTime, endTime: LocalDateTime)

    @Transaction
    fun updateLessons(timetableId: Long, lessons: List<Lesson>): List<Lesson> {
        val startTime = lessons.minOf { it.startTime }
        val endTime = lessons.maxOf { it.endTime }
        deleteLessonByDates(timetableId, startTime, endTime)
        insertLessons(lessons)
        return getLessonsByDates(
            timetableId, startTime, endTime
        )
    }
}

