package com.yunuscagliyan.soccerapp.data.repository

import com.yunuscagliyan.soccerapp.data.api.SportDataApi
import com.yunuscagliyan.soccerapp.data.model.Team
import com.yunuscagliyan.soccerapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class SportRepository @Inject constructor(
    private val api: SportDataApi
) {

    suspend fun getLeagueTeams(
        searchQuery:String=""
    ): Flow<Resource<List<Team?>>> = flow {
        emit(Resource.loading(null))
        try {
            val response = api.getLeagueTeams(searchQuery)
            emit(Resource.success(response.teams))
        } catch (e: Exception) {
            emit(Resource.error(e.message, null))
        }
    }
}