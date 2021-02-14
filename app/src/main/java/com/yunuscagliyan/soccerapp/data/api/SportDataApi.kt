package com.yunuscagliyan.soccerapp.data.api

import com.yunuscagliyan.soccerapp.data.model.FixtureResult
import com.yunuscagliyan.soccerapp.data.model.LeagueTeamsResult
import retrofit2.http.GET
import retrofit2.http.Query

//https://app.sportdataapi.com/api/v1/soccer/teams?apikey=e578cfd0-6d34-11eb-8cda-0b81196afa50&country_id=48&league_id=314
interface SportDataApi {
    @GET("teams")
    suspend fun getLeagueTeams(
        @Query("teamName") teamName: String
    ): LeagueTeamsResult

    @GET("fixture")
    suspend fun getFixture(): FixtureResult
}