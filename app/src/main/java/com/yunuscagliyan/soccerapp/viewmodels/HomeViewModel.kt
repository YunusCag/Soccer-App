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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: SportRepository
):ViewModel() {

    private val _teamList:MutableLiveData<Resource<List<Team?>>> = MutableLiveData()
    val teamList:LiveData<Resource<List<Team?>>> =_teamList

    fun getTeamList()=viewModelScope.launch{
        repository.getLeagueTeams()
            .onEach {
                _teamList.postValue(it)
            }
            .launchIn(viewModelScope)
    }

}