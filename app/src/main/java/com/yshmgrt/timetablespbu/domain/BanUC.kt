package com.yshmgrt.timetablespbu.domain

import com.yshmgrt.timetablespbu.model.Lesson
import com.yshmgrt.timetablespbu.persistance.BanData
import com.yshmgrt.timetablespbu.persistance.BanDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BanUC @Inject constructor(
    private val banDAO: BanDAO
) {

    suspend operator fun invoke(lesson: Lesson, isPermanent: Boolean) =
        withContext(Dispatchers.IO) {
            banDAO.insertBans(
                BanData(
                    name = lesson.name,
                    startTime = lesson.startTime,
                    endTime = lesson.endTime,
                    isPermanent = isPermanent
                )
            )
        }
}