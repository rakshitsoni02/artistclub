package com.dice.artistoverview.vo

import com.dice.artistoverview.R
import com.dice.shared.utils.strings.StringProvider

/**
 * Artist data class used as a UI model
 */
data class Artist(
    val id: String,
    val name: String,
    val type: String?,
    val score: Int,
    val country: String?,
    val shortName: String?,
) {
    fun toUiModel() = ArtistUIModel(
        id = id,
        name = name,
        score = score,
        country = country?.let { StringProvider.of(it) } ?: StringProvider.of(
            R.string.no_key_placeholder
        )
    )
}