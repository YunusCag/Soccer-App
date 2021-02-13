package com.yunuscagliyan.soccerapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yunuscagliyan.soccerapp.data.manager.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager
) : ViewModel() {
    private val _preferencesFlow = preferenceManager.preferencesFlow
    val screenModePreference = _preferencesFlow.asLiveData()


    fun updateIsNightMode(isNight: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        preferenceManager.updateIsNightMode(isNight)
    }
}