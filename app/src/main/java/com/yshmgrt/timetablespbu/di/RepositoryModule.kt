package com.yshmgrt.timetablespbu.di

import com.yshmgrt.timetablespbu.data.lesson.RemoteLessonRepository
import com.yshmgrt.timetablespbu.data.lesson.RemoteLessonRepositoryImpl
import com.yshmgrt.timetablespbu.data.setup.SetupRepository
import com.yshmgrt.timetablespbu.data.setup.SetupRepositoryImpl
import com.yshmgrt.timetablespbu.data.timetable.TimetableIdRepository
import com.yshmgrt.timetablespbu.data.timetable.TimetableIdRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideSetupRepository(
        setupRepositoryImpl: SetupRepositoryImpl
    ): SetupRepository

    @Binds
    abstract fun provideUrlRepository(
        urlRepositoryImpl: TimetableIdRepositoryImpl
    ): TimetableIdRepository

    @Binds
    abstract fun provideRemoteTimetableRepository(
        remoteTimetableRepositoryImpl: RemoteLessonRepositoryImpl
    ): RemoteLessonRepository
}
