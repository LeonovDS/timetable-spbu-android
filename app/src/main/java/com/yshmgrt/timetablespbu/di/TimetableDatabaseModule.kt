package com.yshmgrt.timetablespbu.di

import android.content.Context
import androidx.room.Room
import com.yshmgrt.timetablespbu.persistance.LessonDAO
import com.yshmgrt.timetablespbu.persistance.BanDAO
import com.yshmgrt.timetablespbu.persistance.TimetableDAO
import com.yshmgrt.timetablespbu.persistance.TimetableDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TimetableDatabaseModule {
    @Provides
    @Singleton
    fun provideTimetableDatabase(@ApplicationContext context: Context): TimetableDatabase {
        return Room.databaseBuilder(
            context,
            TimetableDatabase::class.java,
            "timetable_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTimetableDAO(timetableDatabase: TimetableDatabase): TimetableDAO {
        return timetableDatabase.timetableDao()
    }

    @Provides
    @Singleton
    fun provideLessonDAO(timetableDatabase: TimetableDatabase): LessonDAO {
        return timetableDatabase.lessonDao()
    }

    @Provides
    @Singleton
    fun providePermanentBanDAO(timetableDatabase: TimetableDatabase): BanDAO {
        return timetableDatabase.banDao()
    }
}