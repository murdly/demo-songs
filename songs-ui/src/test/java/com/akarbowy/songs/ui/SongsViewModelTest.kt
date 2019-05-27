package com.akarbowy.songs.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.akarbowy.domain.models.SongDM
import com.akarbowy.domain.usecases.GetAllSongsUseCase
import com.akarbowy.domain.usecases.GetLocalSongsUseCase
import com.akarbowy.domain.usecases.GetRemoteSongsUseCase
import com.akarbowy.songs.ui.SongsViewModel.Filter
import com.akarbowy.songs.ui.SongsViewModel.State
import com.akarbowy.utils.rx.InstantRxSchedulerTestRule
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito.`when`

class SongsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val instantRxSchedulerRule = InstantRxSchedulerTestRule()

    @Captor
    private val stateArgumentCaptor = ArgumentCaptor.forClass(State::class.java)

    private val stateObserver: Observer<State> = mock()

    private val filterObserver: Observer<Filter> = mock()

    private val mockGetAllSongsUseCase: GetAllSongsUseCase = mock()

    private val mockGetRemoteSongsUseCase: GetRemoteSongsUseCase = mock()

    private val mockGetLocalSongsUseCase: GetLocalSongsUseCase = mock()

    private val underTest: SongsViewModel =
        SongsViewModel(mockGetAllSongsUseCase, mockGetRemoteSongsUseCase, mockGetLocalSongsUseCase)

    @Test
    fun `show songs when successful response`() {
        `when`(mockGetAllSongsUseCase.execute()) doReturn Single.just(songs_success)
        reset(filterObserver, stateObserver)

        underTest.state.observeForever(stateObserver)
        underTest.filter.observeForever(filterObserver)

        verify(mockGetAllSongsUseCase).execute()
        verify(stateObserver, times(2)).onChanged(stateArgumentCaptor.capture())
        assert(stateArgumentCaptor.firstValue is State.Loading)
        assert(stateArgumentCaptor.lastValue is State.Songs)
        val state = stateArgumentCaptor.lastValue as State.Songs
        assert(state.uim.size == 2)
    }

    @Test
    fun `show empty when successful empty response`() {
        `when`(mockGetAllSongsUseCase.execute()) doReturn Single.just(songs_success_empty)
        reset(filterObserver, stateObserver)

        underTest.state.observeForever(stateObserver)
        underTest.filter.observeForever(filterObserver)

        verify(mockGetAllSongsUseCase).execute()
        verify(stateObserver, times(2)).onChanged(stateArgumentCaptor.capture())
        assert(stateArgumentCaptor.value is State.Empty)
    }

    @Test
    fun `show error when no successful response`() {
        `when`(mockGetAllSongsUseCase.execute()) doReturn Single.error(songs_error)
        reset(filterObserver, stateObserver)

        underTest.state.observeForever(stateObserver)
        underTest.filter.observeForever(filterObserver)

        verify(mockGetAllSongsUseCase).execute()
        verify(stateObserver, times(2)).onChanged(stateArgumentCaptor.capture())
        assert(stateArgumentCaptor.value is State.Error)
    }

    @Test
    fun `do not reload songs for the same filter`() {
        `when`(mockGetAllSongsUseCase.execute()) doReturn Single.error(songs_error)
        reset(filterObserver, stateObserver)

        underTest.state.observeForever(stateObserver)
        underTest.filter.observeForever(filterObserver)

        underTest.setFilter(Filter.All)

        verify(mockGetAllSongsUseCase, times(1)).execute()
    }

    companion object {
        private val songs_success = listOf(
            SongDM("remote", "", null),
            SongDM("local", "", null)
        )

        private val songs_success_empty = emptyList<SongDM>()

        private val songs_error = Throwable()
    }

}