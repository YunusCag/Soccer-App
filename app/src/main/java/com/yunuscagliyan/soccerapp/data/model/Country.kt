package com.yunuscagliyan.soccerapp.data.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("continent")
    var continent: String?,
    @SerializedName("country_code")
    var countryCode: String?,
    @SerializedName("country_id")
    var countryId: Int?,
    @SerializedName("name")
    var name: String?
)
