package com.akarbowy.songs.data

import com.akarbowy.domain.models.SongDM
import com.akarbowy.domain.repositories.SongsRepository
import com.akarbowy.songs.data.sources.LocalDataSource
import com.akarbowy.songs.data.sources.RemoteDataSource
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito.`when`


class SongsRepositoryTest {

    private val mockLocalDataSource: LocalDataSource = mock()

    private val mockRemoteDataSource: RemoteDataSource = mock()

    private val underTest: SongsRepository =
        SongsRepositoryImpl(mockLocalDataSource, mockRemoteDataSource)

    @Test
    fun `has songs from local and remote if both sources are successful`() {
        `when`(mockLocalDataSource.loadSongs()).thenReturn(Single.just(localSongs))
        `when`(mockRemoteDataSource.loadSongs()).thenReturn(Single.just(remoteSongs))

        underTest.getAllSongs().test()
            .assertValue(localSongs.plus(remoteSongs))
    }

    @Test
    fun `error is not propagated if one of the sources fail`() {
        `when`(mockLocalDataSource.loadSongs()).thenReturn(Single.error(Throwable()))
        `when`(mockRemoteDataSource.loadSongs()).thenReturn(Single.just(remoteSongs))

        underTest.getAllSongs().test()
            .assertNoErrors()
    }

    @Test
    fun `has songs from remote if local fails`() {
        `when`(mockLocalDataSource.loadSongs()).thenReturn(Single.error(Throwable()))
        `when`(mockRemoteDataSource.loadSongs()).thenReturn(Single.just(remoteSongs))

        underTest.getAllSongs()
            .test()
            .assertValue(remoteSongs)
    }

    @Test
    fun `has songs from local if remote fails`() {
        `when`(mockLocalDataSource.loadSongs()).thenReturn(Single.just(localSongs))
        `when`(mockRemoteDataSource.loadSongs()).thenReturn(Single.error(Throwable()))

        underTest.getAllSongs()
            .test()
            .assertValue(localSongs)
    }

    companion object {
        private val localSongs = listOf(SongDM("remote", "", null))
        private val remoteSongs = listOf(SongDM("local", "", null))
    }

}