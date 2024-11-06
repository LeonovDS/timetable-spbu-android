package com.yshmgrt.timetablespbu.domain

import android.util.Log
import arrow.core.flatMap
import com.yshmgrt.timetablespbu.data.ban.BanRepository
import com.yshmgrt.timetablespbu.data.lesson.LocalLessonRepository
import com.yshmgrt.timetablespbu.data.lesson.RemoteLessonRepository
import com.yshmgrt.timetablespbu.data.timetable.TimetableRepository
import com.yshmgrt.timetablespbu.model.LoadingState
import com.yshmgrt.timetablespbu.persistance.Ban
import com.yshmgrt.timetablespbu.persistance.Lesson
import com.yshmgrt.timetablespbu.util.endOfDay
import com.yshmgrt.timetablespbu.util.endOfWeek
import com.yshmgrt.timetablespbu.util.startOfDay
import com.yshmgrt.timetablespbu.util.startOfWeek
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject

class CachingLessonLoader @Inject constructor(
    private val timetableRepository: TimetableRepository,
    private val localLessonRepository: LocalLessonRepository,
    private val remoteLessonRepository: RemoteLessonRepository,
    private val banRepository: BanRepository
) {
    suspend fun loadLessons(
        timetableId: Long,
        date: LocalDateTime,
    ): Flow<LoadingState<List<Lesson>>> = withContext(Dispatchers.IO) {
        val startDate = date.date.startOfWeek().startOfDay()
        val endDate = date.date.endOfWeek().endOfDay()
        val bans = banRepository.getBan().fold({ emptyList() }) { it }

        val localFlow = flow {
            emit(LoadingState.Loading)
            localLessonRepository.getLessonsByDate(timetableId, startDate, endDate)
                .map { it.filterBannedLessons(bans) }
                .onLeft { emit(LoadingState.Error(it.toString())) }
                .onRight { emit(LoadingState.Ready(it)) }
        }.onEach {
            Log.d(TAG, "local: $it")
        }
        val remoteFlow = flow {
            emit(LoadingState.Loading)
            timetableRepository.getTimetableById(timetableId)
                .flatMap { timetable ->
                    remoteLessonRepository.getLessonList(timetable, startDate.date)
                }.flatMap { lessonList ->
                    localLessonRepository.insertOrUpdate(timetableId, lessonList)
                }.map {
                    it.filterBannedLessons(bans)
                }.onLeft {
                    emit(LoadingState.Error(it.toString()))
                }.onRight {
                    emit(LoadingState.Ready(it))
                }
        }.onEach {
            Log.d(TAG, "remote: $it")
        }

        localFlow.combine(remoteFlow) { local, remote ->
            when {
                remote is LoadingState.Ready -> remote
                local is LoadingState.Ready -> local
                local is LoadingState.Error && remote is LoadingState.Error -> LoadingState.Error("local: ${local.message}, remote: ${remote.message}")
                else -> LoadingState.Loading
            }
        }.onEach {
            Log.d(TAG, "zipped: $it")
        }
    }


    private fun List<Lesson>.filterBannedLessons(
        bans: List<Ban>
    ): List<Lesson> {
        val permanentBans = bans.filter { it.isPermanent }.map {
            it.name to it.startTime.time to it.startTime.dayOfWeek
        }.toSet()
        val temporaryBans = bans.filterNot { it.isPermanent }.map {
            it.name to it.startTime to it.endTime
        }
        return filter {
            (it.name to it.startTime.time to it.startTime.dayOfWeek) !in permanentBans
        }.filter {
            (it.name to it.startTime to it.endTime) !in temporaryBans
        }
    }

    companion object {
        const val TAG = "LoadLessonUC"
    }
}
