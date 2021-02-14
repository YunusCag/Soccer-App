package com.yunuscagliyan.soccerapp.data.model


import com.google.gson.annotations.SerializedName

data class FixtureResult(
    @SerializedName("fixture")
    var fixture: List<Fixture?>?
) {

}