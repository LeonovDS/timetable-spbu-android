package com.yshmgrt.timetablespbu.data.ban

import android.util.Log
import arrow.core.Either
import com.yshmgrt.timetablespbu.data.setup.Error
import com.yshmgrt.timetablespbu.persistance.BanDAO
import com.yshmgrt.timetablespbu.persistance.Ban
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject

class BanRepositoryImpl @Inject constructor(private val banDAO: BanDAO) : BanRepository {
    override fun getBan(): Either<Error, List<Ban>> = Either.Companion.catch {
        banDAO.getBans()
    }.mapLeft {
        Log.e(TAG, "getBan: error: $it")
        Error.UnknownException(it)
    }

    override fun insertBan(ban: Ban): Either<Error, Unit> = Either.Companion.catch {
        banDAO.insertBans(ban)
    }.mapLeft {
        Log.e(TAG, "insertBan: error: $it")
        Error.UnknownException(it)
    }

    override fun deleteBan(name: String, startTime: LocalDateTime): Either<Error, Unit> =
        Either.Companion.catch {
            banDAO.deleteBan(name, startTime)
        }.mapLeft {
            Log.e(TAG, "deleteBan: error: $it")
            Error.UnknownException(it)
        }

    companion object {
        const val TAG = "BanRepository"
    }
}