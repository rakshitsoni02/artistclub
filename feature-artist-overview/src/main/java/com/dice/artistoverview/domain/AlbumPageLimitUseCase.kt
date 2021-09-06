package com.dice.artistoverview.domain

import com.dice.artistoverview.data.repo.ArtistRepository
import com.dice.shared.di.IoDispatcher
import com.dice.shared.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumPageLimitUseCase @Inject constructor(
    private val artistRepository: ArtistRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : UseCase<String, Boolean>(ioDispatcher) {

    override suspend fun execute(parameters: String): Boolean {
        return artistRepository.getAlbumLimitState(parameters)
    }
}