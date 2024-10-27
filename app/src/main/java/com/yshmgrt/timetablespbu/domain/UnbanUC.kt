package com.yshmgrt.timetablespbu.domain

import com.yshmgrt.timetablespbu.model.Ban
import com.yshmgrt.timetablespbu.persistance.BanDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UnbanUC @Inject constructor(
    private val banDAO: BanDAO
) {
    suspend operator fun invoke(permanentBan: Ban) = withContext(Dispatchers.IO) {
        banDAO.deleteBan(permanentBan.name, permanentBan.startTime)
    }
}