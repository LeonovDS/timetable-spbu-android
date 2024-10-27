package com.yshmgrt.timetablespbu.domain

import com.yshmgrt.timetablespbu.model.Ban
import com.yshmgrt.timetablespbu.persistance.BanDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetBansUC @Inject constructor(
    private val banDAO: BanDAO
) {
    suspend operator fun invoke() =
        withContext(Dispatchers.IO) {
            banDAO.getBans().map {
                Ban(
                    name = it.name,
                    startTime = it.startTime,
                    endTime = it.endTime
                )
            }
        }
}
