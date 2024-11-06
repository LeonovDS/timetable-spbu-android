package com.yshmgrt.timetablespbu.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        Timetable::class,
        Lesson::class,
        Ban::class,
    ],
    version = 1
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class TimetableDatabase : RoomDatabase() {
    abstract fun timetableDao(): TimetableDAO
    abstract fun lessonDao(): LessonDAO
    abstract fun banDao(): BanDAO
}