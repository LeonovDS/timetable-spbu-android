package com.yshmgrt.timetablespbu.persistance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.datetime.LocalDateTime

@Dao
interface BanDAO {
    @Query("SELECT * FROM Ban")
    fun getBans(): List<Ban>

    @Insert
    fun insertBans(vararg lessons: Ban)

    @Query("DELETE FROM Ban WHERE name = :name AND start_time = :startTime")
    fun deleteBan(name: String, startTime: LocalDateTime)
}
