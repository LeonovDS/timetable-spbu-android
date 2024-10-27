package com.yshmgrt.timetablespbu.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("url")


class UrlRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context) :
    UrlRepository {
    override suspend fun setUrl(url: String) {
        Log.d("UrlRepositoryImpl", "setUrl: $url")
        context.dataStore.edit { it[URL] = url }
    }

    override val url = context.dataStore.data.map { it[URL] }

    companion object {
        val URL = stringPreferencesKey("url")
    }
}
