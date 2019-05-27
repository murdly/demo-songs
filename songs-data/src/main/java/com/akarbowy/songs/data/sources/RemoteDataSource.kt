package com.akarbowy.songs.data.sources

import com.akarbowy.domain.models.SongDM
import io.reactivex.Single


interface RemoteDataSource {

    fun loadSongs(): Single<List<SongDM>>

}