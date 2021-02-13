package com.yunuscagliyan.soccerapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yunuscagliyan.soccerapp.data.manager.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val preferenceManager:PreferenceManager
):ViewModel() {
    private val _preferenceFlow=preferenceManager.preferencesFlow
    val screenModePreference=_preferenceFlow.asLiveData()



}