package com.yshmgrt.timetablespbu.domain

import com.yshmgrt.timetablespbu.model.Lesson
import com.yshmgrt.timetablespbu.persistance.BanDAO
import com.yshmgrt.timetablespbu.persistance.BanData
import com.yshmgrt.timetablespbu.persistance.LessonDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject

class LoadLessonsUC @Inject constructor(
    private val lessonDAO: LessonDAO,
    private val banDAO: BanDAO,
) {
    suspend operator fun invoke(
        timetableId: Int,
        startDate: LocalDateTime? = null,
        endDate: LocalDateTime? = null
    ): List<Lesson> = withContext(Dispatchers.IO) {
        val bans = banDAO.getBans()
        val permanentBans = getPermanentBans(bans)
        val temporaryBans = getTemporaryBans(bans)
        val lessons = if (startDate == null || endDate == null) {
            lessonDAO.getLessons(timetableId)
        } else {
            lessonDAO.getLessonsByDates(timetableId, startDate, endDate)
        }
        return@withContext lessons.filter {
            (it.name to it.startTime.time to it.startTime.dayOfWeek) !in permanentBans
        }.filter {
            (it.name to it.startTime) !in temporaryBans
        }.map {
            Lesson(
                name = it.name,
                startTime = it.startTime,
                endTime = it.endTime,
                place = it.place,
                teacher = it.teacher
            )
        }
    }

    private fun getTemporaryBans(bans: List<BanData>) =
        bans.filterNot {
            it.isPermanent
        }.associateBy { it.name to it.startTime }

    private fun getPermanentBans(bans: List<BanData>) =
        bans.filter { it.isPermanent }
            .associateBy { it.name to it.startTime.time to it.startTime.dayOfWeek }
}
