package com.dice.artistoverview.data.model.dto

import com.dice.artistoverview.vo.Album
import com.google.gson.annotations.SerializedName

data class AlbumDtoResponse(
    @SerializedName("id") val id: String,
    @SerializedName("type-id") val typeId: String,
    @SerializedName("score") val score: Int,
    @SerializedName("title") val title: String,
    @SerializedName("primary-type") val primaryType: String?,
    @SerializedName("releases") val releases: List<ReleasesDtoResponse>?,
    @SerializedName("tags") val tags: List<TagsDtoResponse>?,
) {
    fun toModel() = Album(
        id = id,
        title = title,
        typeId = typeId,
        score = score,
        releases = releases?.map { it.title } ?: emptyList(),
    )

}

data class TagsDtoResponse(
    @SerializedName("count") val count: String?,
    @SerializedName("name") val name: String?,
)

data class ReleasesDtoResponse(
    @SerializedName("id") val begin: String,
    @SerializedName("status-id") val ended: String?,
    @SerializedName("title") val title: String,
    @SerializedName("status") val status: String?,
)


data class AlbumEntityDataResponse(
    @SerializedName("created") val createdTime: String,
    @SerializedName("count") val count: Int,
    @SerializedName("offset") val offset: Int,
    @SerializedName("release-groups") val artists: List<AlbumDtoResponse>,
)