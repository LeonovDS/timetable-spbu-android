package com.yshmgrt.timetablespbu.persistance

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity
data class LessonData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "timetable_id") val timetableId: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "start_time") val startTime: LocalDateTime,
    @ColumnInfo(name = "end_time") val endTime: LocalDateTime,
    @ColumnInfo(name = "place") val place: String,
    @ColumnInfo(name = "teacher") val teacher: String,
    @ColumnInfo(name = "is_canceled") val isCanceled: Boolean
)
