package com.yshmgrt.timetablespbu.persistance

import androidx.room.Dao
import androidx.room.Query

@Dao
interface TimetableDAO {
    @Query("SELECT * FROM TimetableData")
    fun getTimetables(): List<TimetableData>

    @Query("INSERT INTO TimetableData (programme, `group`, year, url) VALUES(:programme, :group, :year, :url)")
    fun insertTimetable(programme: String, group: String, year: Int, url: String): Long
}
