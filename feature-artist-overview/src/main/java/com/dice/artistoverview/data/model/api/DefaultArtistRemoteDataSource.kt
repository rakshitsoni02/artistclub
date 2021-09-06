package com.dice.artistoverview.data.model.api

import androidx.annotation.WorkerThread
import com.dice.artistoverview.vo.AlbumPageData
import com.dice.artistoverview.vo.ArtistPageData
import com.dice.shared.utils.network.dataResponse
import javax.inject.Inject
import javax.inject.Singleton

interface ArtistRemoteDataSource {
    @WorkerThread
    suspend fun searchArtist(searchQuery: String, offset: Int): ArtistPageData

    @WorkerThread
    suspend fun getArtistAlbums(artistId: String, offset: Int): AlbumPageData
}

@Singleton
class ArtistRemoteDataSourceImpl @Inject constructor(
    private val endPoint: ArtistEndPoints
) : ArtistRemoteDataSource {

    override suspend fun searchArtist(searchQuery: String, offset: Int): ArtistPageData {
        return endPoint.getArtistsInfo(
            searchQuery = "artist:$searchQuery",
            offset = offset,
            articleLimit = ARTIST_PER_REQUEST_LIMIT,
        ).dataResponse().let { response ->
            ArtistPageData(
                artist = response.artists.map {
                    it.toModel()
                },
                totalItems = response.count
            )
        }
    }

    override suspend fun getArtistAlbums(artistId: String, offset: Int): AlbumPageData {
        return endPoint.getAlbums(
            searchQuery = "arid:${artistId}",
            offset = offset,
            articleLimit = ARTIST_PER_REQUEST_LIMIT,
        ).dataResponse().let { response ->
            AlbumPageData(
                totalItems = response.count,
                albums = response.artists.map { it.toModel() }
            )
        }
    }

    private companion object {
        const val ARTIST_PER_REQUEST_LIMIT = 20
    }
}