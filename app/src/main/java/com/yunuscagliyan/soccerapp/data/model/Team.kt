package com.yunuscagliyan.soccerapp.data.model

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("common_name")
    var commonName: String?,
    @SerializedName("country")
    var country: Country?,
    @SerializedName("logo")
    var logo: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("short_code")
    var shortCode: String?,
    @SerializedName("team_id")
    var teamId: Int?
)
