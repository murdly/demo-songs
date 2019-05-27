package com.akarbowy.songs.data.sources.file

import com.akarbowy.domain.models.SongDM
import com.akarbowy.songs.data.sources.LocalDataSource
import com.akarbowy.songs.data.toDM
import com.akarbowy.utils.resources.ResourceProvider
import com.akarbowy.utils.resources.readText
import com.squareup.moshi.JsonAdapter
import io.reactivex.Single


internal class SongFileProvider(
    private val resourceProvider: ResourceProvider,
    private val adapter: JsonAdapter<List<SongFM>>
) : LocalDataSource {

    override fun loadSongs(): Single<List<SongDM>> {
        val jsonText = resourceProvider.openFile(songsFile).readText()
        val songs = adapter.fromJson(jsonText).orEmpty()

        return Single.just(songs.toDM())
    }

    companion object {
        private const val songsFile = "songs-list.json"
    }

}