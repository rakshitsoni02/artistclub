package com.dice.artistoverview.screens.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dice.artistoverview.domain.ArtistPageLimitUseCase
import com.dice.artistoverview.domain.ArtistSearchUseCase
import com.dice.artistoverview.vo.ArtistUIModel
import com.dice.shared.lifecycle.ResultState
import com.dice.shared.lifecycle.ResultState.Companion.loading
import com.dice.shared.lifecycle.ResultState.Companion.success
import com.dice.shared.utils.kotlin.successOr
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistSearchViewModel @Inject constructor(
    private val artistSearchUseCase: ArtistSearchUseCase,
    private val artistPageLimitUseCase: ArtistPageLimitUseCase
) : ViewModel() {
    private val _searchResults = MutableStateFlow<ResultState<List<ArtistUIModel>>>(
        success(
            emptyList()
        )
    )

    /**
     * artist live data observer gives call back to the UI with respect to current state
     */
    val searchResults: StateFlow<ResultState<List<ArtistUIModel>>> = _searchResults

    private var textQuery = ""
    var isLastPageReached = false

    private var searchJob: Job? = null

    val isLoading: Boolean
        get() = _searchResults.value == loading() || searchJob?.isActive == true

    fun onSearchQueryChanged(query: String) {
        val newQuery = query.trim().takeIf { it.length >= 2 } ?: ""
        if (textQuery != newQuery) {
            textQuery = newQuery
            executeArtistSearch()
        }
    }

    fun onArtistScrolledToBottom() {
        executeArtistSearch()
    }

    private fun executeArtistSearch() {
        // Cancel on going artist searches
        searchJob?.cancel()

        if (textQuery.isEmpty()) {
            clearSearchResults()
            return
        }

        searchJob = viewModelScope.launch {
            // delay in search job and cancelling it helps effectively debounces.
            delay(ARTIST_SEARCH_DELAY)
            artistSearchUseCase(
                textQuery
            ).collect {
                processSearchResult(it)
                updatePageLimitStatus()
            }
        }
    }

    /**
     * flag used to not triggered next page when data limit reached
     * can be improved by using paging library
     */
    private suspend fun updatePageLimitStatus() {
        isLastPageReached = artistPageLimitUseCase(Unit).successOr(false)
    }

    private fun clearSearchResults() {
        //success state updated to not show empty state
        _searchResults.value = success(emptyList())
    }

    private fun processSearchResult(searchResult: ResultState<List<ArtistUIModel>>) {
        _searchResults.value = searchResult
    }

    private companion object {
        const val ARTIST_SEARCH_DELAY = 500L
    }
}