package com.dice.artistoverview.vo

/**
 * ui model for albums as single source for remote/local
 */
data class AlbumUIModel(
    val id: String,
    val title: String,
    val score: Int,
    val releases: List<String>
)