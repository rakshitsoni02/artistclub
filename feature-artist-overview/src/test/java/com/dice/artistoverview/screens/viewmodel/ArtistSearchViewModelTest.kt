package com.dice.artistoverview.screens.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dice.app.assertItems
import com.dice.artistoverview.MainCoroutineRule
import com.dice.artistoverview.data.repo.ArtistRepository
import com.dice.artistoverview.domain.ArtistPageLimitUseCase
import com.dice.artistoverview.domain.ArtistSearchUseCase
import com.dice.artistoverview.runBlockingTest
import com.dice.artistoverview.vo.ArtistUIModel
import com.dice.shared.lifecycle.ResultState
import com.dice.shared.utils.strings.StringProvider
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.internal.util.reflection.FieldSetter

@RunWith(JUnit4::class)
class ArtistSearchViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var coroutineRule = MainCoroutineRule()

    @Mock
    lateinit var artistRepository: ArtistRepository

    lateinit var artistSearchUseCase: ArtistSearchUseCase

    lateinit var artistPageLimitUseCase: ArtistPageLimitUseCase

    private val uiArtists = listOf(
        ArtistUIModel(
            id = "Fetched 1",
            name = "name",
            score = 1,
            country = StringProvider.Companion.of("country")
        )
    )

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        artistSearchUseCase = ArtistSearchUseCase(
            artistRepository,
            coroutineRule.testDispatcher
        )
        artistPageLimitUseCase = ArtistPageLimitUseCase(
            artistRepository,
            coroutineRule.testDispatcher
        )
    }

    @Test
    fun shouldReturnArtistListSuccessfullyTest() = coroutineRule.runBlockingTest {
        //given
        whenever(artistSearchUseCase.invoke(any())) doReturn flowOf(ResultState.success(uiArtists))
        whenever(artistRepository.getArtistLimitState()) doReturn false
        val viewModel = ArtistSearchViewModel(artistSearchUseCase, artistPageLimitUseCase)
        //when
        viewModel.onSearchQueryChanged("some-text")
        //then
        viewModel.searchResults.assertItems(
            ResultState.success(emptyList()),
            ResultState.success(uiArtists)
        )
        assertEquals(viewModel.isLastPageReached, false)
    }

    @Test
    fun shouldReturnErrorOnSearchTextChangeTest() = coroutineRule.runBlockingTest {
        //given
        val errorState = ResultState.error(Exception("error"))
        whenever(artistSearchUseCase.invoke(any())) doReturn flowOf(errorState)
        val viewModel = ArtistSearchViewModel(artistSearchUseCase, artistPageLimitUseCase)
        //when
        viewModel.onSearchQueryChanged("some-text")
        //then
        viewModel.searchResults.assertItems(
            ResultState.success(emptyList()),
            errorState
        )
    }

    @Test
    fun shouldReturnArtistForNewPageTest() = coroutineRule.runBlockingTest {
        //given
        whenever(artistSearchUseCase.invoke(any())) doReturn flowOf(ResultState.success(uiArtists))
        whenever(artistRepository.getArtistLimitState()) doReturn false
        val viewModel = ArtistSearchViewModel(artistSearchUseCase, artistPageLimitUseCase)
        FieldSetter.setField(
            viewModel,
            viewModel.javaClass.getDeclaredField("textQuery"),
            "text"
        )
        //when
        viewModel.onArtistScrolledToBottom()
        //then
        viewModel.searchResults.assertItems(
            ResultState.success(emptyList()),
            ResultState.success(uiArtists)
        )
        assertEquals(viewModel.isLastPageReached, false)
    }
}