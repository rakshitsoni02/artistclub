package com.dice.artistoverview.data.model.api

import com.dice.artistoverview.data.model.dto.AlbumEntityDataResponse
import com.dice.artistoverview.data.model.dto.ArtistEntityDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistEndPoints {

    @GET("ws/2/artist/")
    suspend fun getArtistsInfo(
        @Query("query") searchQuery: String,
        @Query("limit") articleLimit: Int,
        @Query("offset") offset: Int,
        @Query("inc") inc: String = "aliases",
    ): Response<ArtistEntityDataResponse>

    @GET("ws/2/release-group/")
    suspend fun getAlbums(
        @Query("query") searchQuery: String,
        @Query("limit") articleLimit: Int,
        @Query("offset") offset: Int,
        @Query("inc") inc: String = "aliases",
    ): Response<AlbumEntityDataResponse>
}