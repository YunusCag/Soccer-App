package com.yunuscagliyan.soccerapp.viewmodels

import androidx.lifecycle.*
import com.yunuscagliyan.soccerapp.data.model.Team
import com.yunuscagliyan.soccerapp.data.repository.SportRepository
import com.yunuscagliyan.soccerapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: SportRepository,
    private val state: SavedStateHandle
) : ViewModel() {

    private val searchQuery = state.getLiveData("searchQuery", "")
    private val _teamListFlow = searchQuery.asFlow().flatMapLatest { searchQuery ->
        repository.getLeagueTeams(searchQuery = searchQuery)
    }
    val teamList = _teamListFlow.asLiveData()

    private val _homeEventChannel = Channel<HomeEvent>()
    val homeEventChannel = _homeEventChannel.receiveAsFlow()

    fun changeSearchQuery(query: String) {
        searchQuery.postValue(query)
    }

    sealed class HomeEvent {
        object NavigateFixtureScreen : HomeEvent()
    }

}