package com.yshmgrt.timetablespbu.persistance

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(
    indices = [
        Index(value = ["url"], unique = true),
    ]
)
@Serializable
data class Timetable(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "faculty") val faculty: String,
    @ColumnInfo(name = "programme") val programme: String,
    @ColumnInfo(name = "group") val group: String,
    @ColumnInfo(name = "year") val year: String,
    @ColumnInfo(name = "timetable") val timetable: String,
)