package com.dice.artistoverview.vo

data class ArtistDetails(
    val artist: ArtistUIModel,
    val albums: List<AlbumUIModel>
)