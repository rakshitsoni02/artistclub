package com.dice.artistoverview.di

import com.dice.artistoverview.data.model.api.ArtistRemoteDataSource
import com.dice.artistoverview.data.model.api.ArtistRemoteDataSourceImpl
import com.dice.artistoverview.data.repo.ArtistRepository
import com.dice.artistoverview.data.repo.ArtistRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

object ArtistDataModule {
    @Module
    @InstallIn(SingletonComponent::class)
    interface ArtistRepositoryModule {
        /* Exposes the concrete implementation for the interface */
        @Binds
        fun it(it: ArtistRepositoryImpl): ArtistRepository
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface ArtistRemoteDataSourceModule {
        /* Exposes the concrete implementation for the interface */
        @Binds
        fun it(it: ArtistRemoteDataSourceImpl): ArtistRemoteDataSource
    }
}