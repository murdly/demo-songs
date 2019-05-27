package com.akarbowy.songs.data

import com.akarbowy.songs.data.sources.file.SongFM
import com.akarbowy.songs.data.sources.file.SongFileProvider
import com.akarbowy.utils.resources.ResourceProvider
import com.akarbowy.utils.resources.loadJsonAsText
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`


class SongsFileProviderTest {

    private val mockResourceProvider: ResourceProvider = mock()

    private val jsonAdapter: JsonAdapter<List<SongFM>> = Moshi.Builder().build()
        .adapter(Types.newParameterizedType(List::class.java, SongFM::class.java))

    private val underTest = SongFileProvider(mockResourceProvider, jsonAdapter)

    @Test
    fun `loads songs from a json file`() {
        `when`(mockResourceProvider.openFile(anyString())) doReturn
                this::class.java.getResourceAsStream(songsFile)

        underTest.loadSongs().test()
            .assertValueCount(1)
    }

    @Test
    fun `converts json to object`() {
        with(jsonAdapter.fromJson(songsJson)) {
            assertNotNull(this)
            assertEquals(expectedSongs, this)
        }
    }

    companion object {
        private const val songsFile = "/songs-list.json"
        private val songsJson = SongsFileProviderTest::class.java.loadJsonAsText(songsFile)
        private val expectedSongs = listOf(
            SongFM("Caught Up in You", ".38 Special", "1982"),
            SongFM("Fantasy Girl", ".38 Special", "")
        )
    }

}