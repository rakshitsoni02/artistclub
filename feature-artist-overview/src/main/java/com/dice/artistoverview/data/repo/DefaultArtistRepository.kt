package com.dice.artistoverview.data.repo

import com.dice.artistoverview.data.model.api.ArtistRemoteDataSource
import com.dice.artistoverview.vo.Album
import com.dice.artistoverview.vo.Artist
import com.dice.artistoverview.vo.ArtistDetails
import com.dice.artistoverview.vo.ArtistUIModel
import com.dice.shared.lifecycle.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

interface ArtistRepository {
    fun getArtistArticles(searchText: String?): Flow<ResultState<List<ArtistUIModel>>>

    fun getArtistLimitState(): Boolean

    fun getArtistDetail(artistId: String): Flow<ResultState<ArtistDetails>>

    fun getAlbumLimitState(artistId: String): Boolean

}

@Singleton
class ArtistRepositoryImpl @Inject constructor(
    private val artistRemoteDataSource: ArtistRemoteDataSource
) : ArtistRepository {

    var latestException: Exception? = null
        private set


    private val cachedArtist = mutableListOf<Artist>()
    private val cachedAlbums = mutableMapOf<String, List<Album>>()
    private var lastSearchedText: String? = ""
    private var maximumAvailableArtist: Int = 0
    private var maximumAvailableAlbums: Int = 0

    override fun getArtistArticles(
        searchText: String?
    ): Flow<ResultState<List<ArtistUIModel>>> =
        flow {
            emit(ResultState.loading())
            if (lastSearchedText != searchText) {
                cachedArtist.clear()
            }
            lastSearchedText = searchText
            if (searchText.isNullOrEmpty()) {
                val e = Exception("Search text is null")
                latestException = e
                throw e
            }
            val pageData = artistRemoteDataSource.searchArtist(searchText, cachedArtist.size)
            maximumAvailableArtist = pageData.totalItems
            cachedArtist.addAll(pageData.artist)
            emit(ResultState.success(cachedArtist.map { artist ->
                artist.toUiModel()
            }))
        }

    override fun getArtistLimitState(): Boolean = cachedArtist.size >= maximumAvailableArtist

    override fun getAlbumLimitState(artistId: String): Boolean =
        cachedAlbums[artistId]?.size ?: 0 >= maximumAvailableAlbums

    override fun getArtistDetail(artistId: String): Flow<ResultState<ArtistDetails>> =
        flow {
            emit(ResultState.loading())
            if (artistId.isEmpty()) {
                val e = Exception("artistId is null")
                latestException = e
                throw e
            }
            val cache = cachedAlbums[artistId] ?: emptyList()
            val pageData = artistRemoteDataSource.getArtistAlbums(artistId, cache.size)
            maximumAvailableAlbums = pageData.totalItems
            // merging cache + new page data and creating an update, can be improve using database
            val albums = cache + pageData.albums
            cachedAlbums[artistId] = albums
            val updatedDetails = ArtistDetails(
                artist = getUiArtist(artistId),
                albums = albums.map { it.toUIModel() }
            )
            emit(ResultState.success(updatedDetails))
        }

    private fun getUiArtist(artistId: String): ArtistUIModel =
        cachedArtist.first { it.id == artistId }.toUiModel()
}