package com.yshmgrt.timetablespbu.ui.settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.yshmgrt.timetablespbu.data.ban.BanRepository
import com.yshmgrt.timetablespbu.model.LoadingState
import com.yshmgrt.timetablespbu.persistance.Ban
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BanViewModel @Inject constructor(
    private val banRepository: BanRepository
) : ViewModel() {
    val bans: MutableState<LoadingState<List<Ban>>> =
        mutableStateOf<LoadingState<List<Ban>>>(LoadingState.Loading)

    init {
        loadBans()
    }

    fun loadBans() = viewModelScope.launch(Dispatchers.IO) {
        bans.value = LoadingState.Loading
        bans.value = when (val bans = banRepository.getBan()) {
            is Either.Left -> LoadingState.Error("")
            is Either.Right -> LoadingState.Ready(bans.value)
        }
    }

    fun unban(ban: Ban) {
        viewModelScope.launch(Dispatchers.IO) {
            banRepository.deleteBan(ban.name, ban.startTime)
            loadBans()
        }
    }
}