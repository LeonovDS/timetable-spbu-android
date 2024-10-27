package com.yshmgrt.timetablespbu.persistance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.datetime.LocalDateTime

@Dao
interface BanDAO {
    @Query("SELECT * FROM BanData")
    fun getBans(): List<BanData>

    @Insert
    fun insertBans(vararg lessons: BanData)

    @Query("DELETE FROM BanData WHERE name = :name AND start_time = :startTime")
    fun deleteBan(name: String, startTime: LocalDateTime)
}