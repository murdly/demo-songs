package com.akarbowy.songs.data.sources

import com.akarbowy.domain.models.SongDM
import io.reactivex.Single


interface LocalDataSource {

    fun loadSongs(): Single<List<SongDM>>

}