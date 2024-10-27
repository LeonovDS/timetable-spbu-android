package com.yshmgrt.timetablespbu.persistance

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.datetime.LocalDateTime

@Dao
interface LessonDAO {
    @Query("SELECT * FROM LessonData WHERE timetable_id = :timetableId")
    fun getLessons(timetableId: Int): List<LessonData>

    @Query("SELECT * FROM LessonData WHERE id = :id")
    fun getLesson(id: Int): LessonData

    @Query("SELECT * FROM LessonData WHERE name = :name AND start_time = :startTime")
    fun getLessons(name: String, startTime: LocalDateTime): List<LessonData>

    @Query(
        "SELECT * FROM LessonData " +
            "WHERE timetable_id = :timetableId " +
            "AND start_time >= :startTime " +
            "AND end_time <= :endTime"
    )
    fun getLessonsByDates(
        timetableId: Int,
        startTime: LocalDateTime,
        endTime: LocalDateTime
    ): List<LessonData>

    @Insert
    fun insertLessons(vararg lessons: LessonData)

    @Delete
    fun deleteLesson(lesson: LessonData)

    @Query("DELETE FROM LessonData WHERE name = :name AND start_time = :startTime")
    fun deleteLesson(name: String, startTime: LocalDateTime)
}
