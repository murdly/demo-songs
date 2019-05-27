package com.akarbowy.songs.data.sources.network

import com.squareup.moshi.Json


internal data class SongAM(
    @field:Json(name = "trackName")
    val name: String,

    @field:Json(name = "artistName")
    val artist: String,

    @field:Json(name = "releaseDate")
    val year: String
)