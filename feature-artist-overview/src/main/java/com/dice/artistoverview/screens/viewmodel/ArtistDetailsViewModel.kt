package com.dice.artistoverview.screens.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dice.artistoverview.domain.AlbumPageLimitUseCase
import com.dice.artistoverview.domain.ArtistDetailsUseCase
import com.dice.artistoverview.screens.view.ArtistDetailsFragment
import com.dice.artistoverview.vo.ArtistDetails
import com.dice.shared.lifecycle.ResultState
import com.dice.shared.utils.kotlin.successOr
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val artistSearchUseCase: ArtistDetailsUseCase,
    private val albumPageLimitUseCase: AlbumPageLimitUseCase,
) : ViewModel() {
    private var artistId = ""
    var isLimitReached = false

    private var artistDetailJob: Job? = null

    val isLoading: Boolean
        get() = _artistDetailsResult.value == ResultState.loading() || artistDetailJob?.isActive == true

    private val _artistDetailsResult =
        MutableStateFlow<ResultState<ArtistDetails>>(ResultState.loading())

    /**
     * Return artist details to the UI.
     */
    val artistDetailsResult: StateFlow<ResultState<ArtistDetails>> = _artistDetailsResult


    init {
        artistId = savedStateHandle.getLiveData<String>(ArtistDetailsFragment.ARGS_ARTIST_ID).value
            ?: throw IllegalArgumentException("artistId required")
        triggerGetArtistDetail()
    }

    fun onAlbumsReachedBottom() {
        triggerGetArtistDetail()
    }

    private fun triggerGetArtistDetail() {
        artistDetailJob = viewModelScope.launch {
            artistSearchUseCase(artistId).collect {
                processArtistDetail(it)
                updateDataLimit()
            }
        }
    }

    private suspend fun updateDataLimit() {
        isLimitReached = albumPageLimitUseCase(artistId).successOr(false)
    }

    private fun processArtistDetail(resultState: ResultState<ArtistDetails>) {
        _artistDetailsResult.value = resultState
    }
}