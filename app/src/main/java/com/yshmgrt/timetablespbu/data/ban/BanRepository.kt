package com.yshmgrt.timetablespbu.data.ban

import arrow.core.Either
import com.yshmgrt.timetablespbu.data.setup.Error
import com.yshmgrt.timetablespbu.persistance.Ban
import kotlinx.datetime.LocalDateTime

interface BanRepository {
    fun getBan(): Either<Error, List<Ban>>
    fun insertBan(ban: Ban): Either<Error, Unit>
    fun deleteBan(name: String, startTime: LocalDateTime): Either<Error, Unit>
}