package com.dice.artistoverview.vo

/**
 * data class Release Album of an artist
 */
data class Album(
    val id: String,
    val title: String,
    val score: Int,
    val typeId: String?,
    val releases: List<String>
) {

    fun toUIModel() = AlbumUIModel(
        id = id,
        title = title,
        releases = releases,
        score = score
    )
}