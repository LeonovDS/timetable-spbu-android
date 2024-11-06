package com.yshmgrt.timetablespbu.persistance

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity
data class Ban(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "start_time") val startTime: LocalDateTime,
    @ColumnInfo(name = "end_time") val endTime: LocalDateTime,
    @ColumnInfo(name = "is_permanent") val isPermanent: Boolean,
)
