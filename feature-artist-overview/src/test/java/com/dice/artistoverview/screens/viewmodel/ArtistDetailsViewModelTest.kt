package com.dice.artistoverview.screens.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.dice.artistoverview.MainCoroutineRule
import com.dice.artistoverview.data.repo.ArtistRepository
import com.dice.artistoverview.domain.AlbumPageLimitUseCase
import com.dice.artistoverview.domain.ArtistDetailsUseCase
import com.dice.artistoverview.runBlockingTest
import com.dice.artistoverview.vo.ArtistDetails
import com.dice.artistoverview.vo.ArtistUIModel
import com.dice.shared.utils.strings.StringProvider
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is
import org.hamcrest.core.IsNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class ArtistDetailsViewModelTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var coroutineRule = MainCoroutineRule()

    @Mock
    lateinit var artistRepository: ArtistRepository

    lateinit var artistDetailsUseCase: ArtistDetailsUseCase

    lateinit var albumPageLimitUseCase: AlbumPageLimitUseCase


    private val artistDetails = ArtistDetails(
        ArtistUIModel(
            id = "Fetched 1",
            name = "name",
            score = 1,
            country = StringProvider.of("country")
        ), listOf()
    )

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        artistDetailsUseCase = ArtistDetailsUseCase(
            artistRepository,
            coroutineRule.testDispatcher
        )
        albumPageLimitUseCase = AlbumPageLimitUseCase(
            artistRepository,
            coroutineRule.testDispatcher
        )
    }

    @Test
    fun onNullArtistIdPassedTest() = coroutineRule.runBlockingTest {
        //given
        val savedStateHandle = SavedStateHandle()
        var exception: java.lang.Exception? = null
        //when
        try {
            val viewModel = ArtistDetailsViewModel(
                savedStateHandle,
                artistDetailsUseCase,
                albumPageLimitUseCase
            )
            viewModel.onAlbumsReachedBottom()
        } catch (e: Exception) {
            exception = e
            //then
            assertThat(exception, Is.`is`(IsNull.notNullValue()))
        }
    }
}


