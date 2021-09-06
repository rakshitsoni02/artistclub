package com.dice.artistoverview.data.model.dto

import com.dice.artistoverview.vo.Artist
import com.google.gson.annotations.SerializedName

data class ArtistDtoResponse(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String?,
    @SerializedName("type-id") val typeId: String,
    @SerializedName("score") val score: Int,
    @SerializedName("name") val name: String,
    @SerializedName("sort-name") val shortName: String?,
    @SerializedName("country") val country: String?,
    @SerializedName("disambiguation") val disambiguation: String?,
    @SerializedName("life-span") val lifeSpan: ArtistLifeSpanDtoResponse,
) {
    fun toModel() = Artist(
        id = id,
        name = name,
        type = type,
        score = score,
        country = country,
        shortName = shortName,
    )

}

data class ArtistLifeSpanDtoResponse(
    @SerializedName("begin") val begin: String,
    @SerializedName("ended") val ended: String?,
)

data class ArtistEntityDataResponse(
    @SerializedName("created") val createdTime: String,
    @SerializedName("count") val count: Int,
    @SerializedName("offset") val offset: Int,
    @SerializedName("artists") val artists: List<ArtistDtoResponse>,
)