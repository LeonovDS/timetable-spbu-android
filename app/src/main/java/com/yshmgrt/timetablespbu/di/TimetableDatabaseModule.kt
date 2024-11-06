package com.yshmgrt.timetablespbu.di

import android.content.Context
import androidx.room.Room
import com.yshmgrt.timetablespbu.data.ban.BanRepository
import com.yshmgrt.timetablespbu.data.ban.BanRepositoryImpl
import com.yshmgrt.timetablespbu.data.lesson.LocalLessonRepository
import com.yshmgrt.timetablespbu.data.lesson.LocalLessonRepositoryImpl
import com.yshmgrt.timetablespbu.data.timetable.TimetableRepository
import com.yshmgrt.timetablespbu.data.timetable.TimetableRepositoryImpl
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
    fun provideBanRepository(timetableDatabase: TimetableDatabase): BanRepository {
        return BanRepositoryImpl(timetableDatabase.banDao())
    }

    @Provides
    @Singleton
    fun provideLessonRepository(timetableDatabase: TimetableDatabase): LocalLessonRepository {
        return LocalLessonRepositoryImpl(timetableDatabase.lessonDao())
    }

    @Provides
    @Singleton
    fun providesTimetableDAO(timetableDatabase: TimetableDatabase): TimetableRepository {
        return TimetableRepositoryImpl(timetableDatabase.timetableDao())
    }
}
