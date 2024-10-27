package com.yshmgrt.timetablespbu.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface UrlRepository {
    suspend fun setUrl(url: String)
    val url: Flow<String?>
}