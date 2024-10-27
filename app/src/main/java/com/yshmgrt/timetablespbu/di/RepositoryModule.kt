package com.yshmgrt.timetablespbu.di

import com.yshmgrt.timetablespbu.data.RemoteTimetableRepository
import com.yshmgrt.timetablespbu.data.RemoteTimetableRepositoryImpl
import com.yshmgrt.timetablespbu.data.SetupRepository
import com.yshmgrt.timetablespbu.data.SetupRepositoryImpl
import com.yshmgrt.timetablespbu.data.UrlRepository
import com.yshmgrt.timetablespbu.data.UrlRepositoryImpl
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
        urlRepositoryImpl: UrlRepositoryImpl
    ): UrlRepository

    @Binds
    abstract fun provideRemoteTimetableRepository(
        remoteTimetableRepositoryImpl: RemoteTimetableRepositoryImpl
    ): RemoteTimetableRepository
}