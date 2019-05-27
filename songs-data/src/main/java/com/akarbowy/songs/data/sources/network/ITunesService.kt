package com.akarbowy.songs.data.sources.network

import com.akarbowy.domain.models.SongDM
import com.akarbowy.songs.data.sources.RemoteDataSource
import com.akarbowy.songs.data.toDM
import io.reactivex.Single


internal class ITunesService(
    private val api: ITunesApi
) : RemoteDataSource {

    //to keep it simple I hardcoded the artist and other params in here
    override fun loadSongs(): Single<List<SongDM>> =
        api.searchSongs("jack+johnson", mapOf("entity" to "song", "limit" to "25"))
            .map { it.results.orEmpty().toDM() }

}