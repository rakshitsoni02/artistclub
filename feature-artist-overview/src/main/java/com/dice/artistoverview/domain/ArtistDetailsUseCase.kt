package com.dice.artistoverview.domain

import com.dice.artistoverview.vo.ArtistDetails
import com.dice.artistoverview.data.repo.ArtistRepository
import com.dice.shared.di.IoDispatcher
import com.dice.shared.domain.FlowUseCase
import com.dice.shared.lifecycle.ResultState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArtistDetailsUseCase @Inject constructor(
    private val artistRepository: ArtistRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FlowUseCase<String, ArtistDetails>(ioDispatcher) {

    override fun execute(parameters: String): Flow<ResultState<ArtistDetails>> {
        return artistRepository.getArtistDetail(parameters)
    }
}