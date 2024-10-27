package com.yshmgrt.timetablespbu.ui.main.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yshmgrt.timetablespbu.domain.GetBansUC
import com.yshmgrt.timetablespbu.domain.UnbanUC
import com.yshmgrt.timetablespbu.model.Ban
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BanViewModel @Inject constructor(
    private val getBansUC: GetBansUC,
    private val unbanUC: UnbanUC,
) : ViewModel() {
    val _bans = MutableStateFlow<List<Ban>>(emptyList())
    val bans =
        _bans.asStateFlow()

    fun loadBans() {
        viewModelScope.launch {
            _bans.value = getBansUC.invoke()
        }
    }

    fun unban(ban: Ban) {
        viewModelScope.launch {
            unbanUC.invoke(ban)
            loadBans()
        }
    }
}