package com.dice.artistoverview.di

import com.dice.artistoverview.data.model.api.ArtistEndPoints
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ArtistEndpointProviderModule {
    @Singleton
    @Provides
    fun provideArtistServiceEndPoints(retrofit: Retrofit): ArtistEndPoints =
        retrofit.create(ArtistEndPoints::class.java)
}
