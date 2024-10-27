package com.yshmgrt.timetablespbu.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternetController @Inject constructor() {
    private val _isOnline = MutableStateFlow(false)
    val isOnline = _isOnline.asStateFlow()

    fun setIsOnline(isOnline: Boolean) {
        _isOnline.value = isOnline
    }
}