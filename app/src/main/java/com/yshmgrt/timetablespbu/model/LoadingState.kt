package com.yshmgrt.timetablespbu.model

sealed interface LoadingState<out T> {
    data object Loading : LoadingState<Nothing>
    data class Error(val message: String) : LoadingState<Nothing>
    data class Ready<T>(val data: T) : LoadingState<T>
}
