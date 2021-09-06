package com.dice.artistoverview.domain

import com.dice.artistoverview.vo.ArtistUIModel
import com.dice.artistoverview.data.repo.ArtistRepository
import com.dice.shared.di.IoDispatcher
import com.dice.shared.domain.FlowUseCase
import com.dice.shared.lifecycle.ResultState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArtistSearchUseCase @Inject constructor(
    private val artistRepository: ArtistRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FlowUseCase<String?, List<ArtistUIModel>>(ioDispatcher) {

    override fun execute(parameters: String?): Flow<ResultState<List<ArtistUIModel>>> {
        return artistRepository.getArtistArticles(parameters)
    }
}