package com.akarbowy.domain.repositories

import com.akarbowy.domain.models.SongDM
import io.reactivex.Single


interface SongsRepository {

    fun getAllSongs(): Single<List<SongDM>>

    fun getRemoteSongs(): Single<List<SongDM>>

    fun getLocalSongs(): Single<List<SongDM>>
}