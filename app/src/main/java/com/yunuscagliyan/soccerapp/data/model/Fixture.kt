package com.yunuscagliyan.soccerapp.data.model

import com.google.gson.annotations.SerializedName

data class Fixture(
    @SerializedName("matches")
    var matches: List<Matche?>?,
    @SerializedName("weekIndex")
    var weekIndex: String?
) {
    data class Matche(
        @SerializedName("away_team")
        var awayTeam: AwayTeam?,
        @SerializedName("group")
        var group: Group?,
        @SerializedName("home_team")
        var homeTeam: HomeTeam?,
        @SerializedName("league_id")
        var leagueId: Int?,
        @SerializedName("match_start")
        var matchStart: String?,
        @SerializedName("match_start_iso")
        var matchStartIso: String?,
        @SerializedName("match_id")
        var matchId: Int?,
        @SerializedName("minute")
        var minute: Any?,
        @SerializedName("referee_id")
        var refereeId: Int?,
        @SerializedName("season_id")
        var seasonId: Int?,
        @SerializedName("stage")
        var stage: Stage?,
        @SerializedName("stats")
        var stats: Stats?,
        @SerializedName("status")
        var status: String?,
        @SerializedName("status_code")
        var statusCode: Int?,
        @SerializedName("venue")
        var venue: Venue?
    ) {
        data class AwayTeam(
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
        ) {
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
        }

        data class Group(
            @SerializedName("group_name")
            var groupName: String?,
            @SerializedName("group_id")
            var groupId: Int?
        )

        data class HomeTeam(
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
        ) {
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
        }
        data class Stage(
            @SerializedName("name")
            var name: String?,
            @SerializedName("stage_id")
            var stageId: Int?
        )

        data class Stats(
            @SerializedName("away_score")
            var awayScore: Int?,
            @SerializedName("et_score")
            var etScore: Any?,
            @SerializedName("ft_score")
            var ftScore: String?,
            @SerializedName("home_score")
            var homeScore: Int?,
            @SerializedName("ht_score")
            var htScore: String?,
            @SerializedName("ps_score")
            var psScore: Any?
        )

        data class Venue(
            @SerializedName("capacity")
            var capacity: Int?,
            @SerializedName("city")
            var city: String?,
            @SerializedName("country_id")
            var countryId: Int?,
            @SerializedName("name")
            var name: String?,
            @SerializedName("venue_id")
            var venueId: Int?
        )
    }
}