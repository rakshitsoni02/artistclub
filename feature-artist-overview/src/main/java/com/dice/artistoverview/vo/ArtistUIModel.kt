package com.dice.artistoverview.vo

import com.dice.shared.utils.strings.StringProvider

/**
 * ui model for artist as single source for remote/local
 */
data class ArtistUIModel(
    val id: String,
    val name: String,
    val score: Int,
    val country: StringProvider
)