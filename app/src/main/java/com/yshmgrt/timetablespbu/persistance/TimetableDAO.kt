package com.yshmgrt.timetablespbu.persistance

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import arrow.core.compose

@Dao
interface TimetableDAO {
    @Query("SELECT * FROM Timetable")
    fun getTimetables(): List<Timetable>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    fun insertTimetable(timetable: Timetable): Long

    @Query("SELECT * FROM Timetable WHERE id = :id")
    fun getTimetableById(id: Long): Timetable

    @Delete
    fun deleteTimetable(timetable: Timetable)

    @Transaction()
    fun insertAndGetTimetable(timetable: Timetable) =
        (::getTimetableById compose ::insertTimetable)(timetable)
}
