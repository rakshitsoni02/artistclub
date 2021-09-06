package com.dice.artistoverview.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dice.artistoverview.MainCoroutineRule
import com.dice.artistoverview.data.repo.ArtistRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class ArtistDetailsUseCaseTest {
    @Mock
    lateinit var artistRepository: ArtistRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun submit() {
        ArtistDetailsUseCase(
            artistRepository,
            coroutineRule.testDispatcher
        ).invoke("text-query")
        //then
        verify(1) {
            artistRepository.getArtistDetail(any())
        }
    }
}