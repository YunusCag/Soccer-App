package com.yunuscagliyan.soccerapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yunuscagliyan.soccerapp.data.model.Team
import com.yunuscagliyan.soccerapp.data.repository.SportRepository
import com.yunuscagliyan.soccerapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@HiltViewModel
class HomeViewModel constructor(
    private val repository: SportRepository
):ViewModel() {

    private val _teamList:MutableLiveData<Resource<List<Team?>>> = MutableLiveData()
    val teamList:LiveData<Resource<List<Team?>>> =_teamList

    fun getTeamList()=viewModelScope.launch(Dispatchers.IO) {
        repository.getLeagueTeams()
            .onEach {
                _teamList.postValue(it)
            }
    }
    
}