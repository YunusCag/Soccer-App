package com.yunuscagliyan.soccerapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yunuscagliyan.soccerapp.data.model.Fixture
import com.yunuscagliyan.soccerapp.data.repository.SportRepository
import com.yunuscagliyan.soccerapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FixtureViewModel @Inject constructor(
    private val repository: SportRepository
) : ViewModel() {

    private val _fixtures: MutableLiveData<Resource<List<Fixture?>>> = MutableLiveData()
    val fixtures: LiveData<Resource<List<Fixture?>>> = _fixtures


    fun getFixtures() = viewModelScope.launch(Dispatchers.IO) {
        repository.getFixture().onEach {
            _fixtures.postValue(it)
        }.launchIn(viewModelScope)
    }

}