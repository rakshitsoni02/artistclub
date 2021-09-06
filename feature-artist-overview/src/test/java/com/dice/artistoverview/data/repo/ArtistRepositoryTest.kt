package com.dice.artistoverview.data.repo

import com.dice.app.assertItems
import com.dice.artistoverview.MainCoroutineRule
import com.dice.artistoverview.data.model.api.ArtistRemoteDataSource
import com.dice.artistoverview.runBlockingTest
import com.dice.artistoverview.vo.*
import com.dice.shared.lifecycle.ResultState
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is
import org.hamcrest.core.IsNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.internal.util.reflection.FieldSetter

@RunWith(JUnit4::class)
class ArtistRepositoryTest {
    @Mock
    lateinit var dataSource: ArtistRemoteDataSource

    @InjectMocks
    lateinit var repository: ArtistRepositoryImpl

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getArtistListSuccessTest() = coroutineRule.runBlockingTest {
        // GIVEN
        val cachedArticles = listOf(artist)

        val uiData = cachedArticles.map { it.toUiModel() }
        val page = ArtistPageData(
            artist = cachedArticles,
            totalItems = 1
        )
        // WHEN
        whenever(dataSource.searchArtist(any(), any())) doReturn page
        // THEN
        repository.getArtistArticles("text").assertItems(
            ResultState.loading(),
            ResultState.success(uiData)
        )
    }

    @Test
    fun getArtistListErrorTest() = coroutineRule.runBlockingTest {
        // GIVEN
        val text = null
        // When
        try {
            repository.getArtistArticles(text).collect { }
        } catch (t: Exception) {
        }
        assertThat(repository.latestException, Is.`is`(IsNull.notNullValue()))
    }

    @Test
    fun getArtistListErrorFromService() = runBlocking {
        // GIVEN
        val text = "any"
        // When
        whenever(
            dataSource.searchArtist(
                any(),
                any()
            )
        ).doAnswer { throw java.lang.Exception("Bad Request") }
        var e: Exception? = null
        try {
            repository.getArtistArticles(text).collect { }
        } catch (t: Exception) {
            e = t
        }
        //Then
        assertThat(e, Is.`is`(IsNull.notNullValue()))
    }


    @Test
    fun getArtistDetailSuccessTest() = coroutineRule.runBlockingTest {
        // GIVEN
        val cachedArticles = listOf(
            Album(
                id = "id 1",
                title = "title",
                typeId = "typeId",
                score = 0,
                releases = emptyList()
            )
        )

        val uiData = cachedArticles.map { it.toUIModel() }
        val page = AlbumPageData(
            albums = cachedArticles,
            totalItems = 1
        )

        val updatedDetails = ArtistDetails(
            artist = artist.toUiModel(),
            albums = uiData
        )
        // WHEN
        //using dao we will not need this operation
        FieldSetter.setField(
            repository,
            repository.javaClass.getDeclaredField("cachedArtist"),
            listOf(artist)
        )
        whenever(dataSource.getArtistAlbums(any(), any())) doReturn page
        // THEN
        repository.getArtistDetail("id").assertItems(
            ResultState.loading(),
            ResultState.success(updatedDetails)
        )
    }

    private companion object {
        val artist = Artist(
            id = "id",
            name = "name",
            type = "type",
            shortName = "shortName",
            score = 0,
            country = "country"
        )
    }

}