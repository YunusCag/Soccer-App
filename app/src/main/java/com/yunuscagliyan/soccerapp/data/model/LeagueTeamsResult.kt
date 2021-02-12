package com.yunuscagliyan.soccerapp.data.model


import com.google.gson.annotations.SerializedName

data class LeagueTeamsResult(
    @SerializedName("data")
    var teams: List<Team?>?,
    @SerializedName("query")
    var query: Query?
) {

    data class Query(
        @SerializedName("apikey")
        var apikey: String?,
        @SerializedName("country_id")
        var countryId: String?,
        @SerializedName("league_id")
        var leagueId: String?
    )
}