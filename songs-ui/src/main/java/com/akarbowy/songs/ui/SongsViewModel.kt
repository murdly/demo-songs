package com.akarbowy.songs.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.akarbowy.domain.models.SongDM
import com.akarbowy.domain.usecases.GetAllSongsUseCase
import com.akarbowy.domain.usecases.GetLocalSongsUseCase
import com.akarbowy.domain.usecases.GetRemoteSongsUseCase
import com.akarbowy.utils.rx.scheduleOn
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject


internal class SongsViewModel @Inject constructor(
    private val allSongsUseCase: GetAllSongsUseCase,
    private val remoteSongsUseCase: GetRemoteSongsUseCase,
    private val localSongsUseCase: GetLocalSongsUseCase
) : ViewModel() {

    private var _filter: MutableLiveData<Filter> = MutableLiveData(Filter.All)

    val filter: LiveData<Filter> = Transformations
        .switchMap(_filter) {
            loadSongs(it)

            MutableLiveData(it)
        }

    val state: LiveData<State>
        get() {
            if (!::_state.isInitialized) {
                _state = MutableLiveData()
                _state.value = State.Loading
            }
            return _state
        }

    private lateinit var _state: MutableLiveData<State>

    private val disposables = CompositeDisposable()

    fun setFilter(value: Filter) {
        if (_filter.value != value) {
            _filter.value = value
        }
    }

    fun retry() {
        _state.value = State.Loading

        loadSongs(_filter.value)
    }

    private fun loadSongs(filter: Filter?) {
        when (filter) {
            Filter.All -> loadAllSongs()
            Filter.Remote -> loadRemoteSongs()
            Filter.Local -> loadLocalSongs()
        }
    }

    private fun loadAllSongs() {
        allSongsUseCase.execute()
            .scheduleOn()
            .subscribeBy(
                onSuccess = { songs -> onSongsLoaded(songs) },
                onError = { error -> onSongsLoadingError(error) }
            ).addTo(disposables)
    }

    private fun loadRemoteSongs() {
        remoteSongsUseCase.execute()
            .scheduleOn()
            .subscribeBy(
                onSuccess = { songs -> onSongsLoaded(songs) },
                onError = { error -> onSongsLoadingError(error) }
            ).addTo(disposables)
    }

    private fun loadLocalSongs() {
        localSongsUseCase.execute()
            .scheduleOn()
            .subscribeBy(
                onSuccess = { songs -> onSongsLoaded(songs) },
                onError = { error -> onSongsLoadingError(error) }
            ).addTo(disposables)
    }

    private fun onSongsLoaded(songs: List<SongDM>) {
        val uim = songs.toUIM()
            .sortedBy { it.name }
            .distinct()

        if (uim.isNotEmpty()) {
            _state.value = State.Songs(uim)
        } else {
            _state.value = State.Empty
        }
    }

    private fun onSongsLoadingError(error: Throwable) {
        Timber.e(error)
        _state.value = State.Error
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    sealed class State {
        object Loading : State()
        class Songs(val uim: List<SongUIM>) : State()
        object Error : State()
        object Empty : State()
    }

    enum class Filter {
        All, Remote, Local
    }

}