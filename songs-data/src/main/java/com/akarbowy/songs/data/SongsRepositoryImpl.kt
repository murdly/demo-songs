package com.akarbowy.songs.data

import com.akarbowy.domain.models.SongDM
import com.akarbowy.domain.repositories.SongsRepository
import com.akarbowy.songs.data.sources.LocalDataSource
import com.akarbowy.songs.data.sources.RemoteDataSource
import io.reactivex.Single
import io.reactivex.functions.BiFunction


internal class SongsRepositoryImpl(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : SongsRepository {

    // depending on requirements we could take different approaches regarding order and errors
    override fun getAllSongs(): Single<List<SongDM>> =
        Single.zip(
            getLocalSongs().onErrorReturn { emptyList() },
            getRemoteSongs().onErrorReturn { emptyList() },
            BiFunction { local, remote -> local.plus(remote) })

    override fun getRemoteSongs() = remote.loadSongs()

    override fun getLocalSongs() = local.loadSongs()

}