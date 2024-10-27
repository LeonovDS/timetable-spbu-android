package com.yshmgrt.timetablespbu.persistance

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TimetableData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "programme") val programme: String,
    @ColumnInfo(name = "group") val group: String,
    @ColumnInfo(name = "year") val year: Int,
    @ColumnInfo(name = "url") val url: String
)