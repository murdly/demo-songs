package com.akarbowy.songs.data.sources.file

import com.squareup.moshi.Json


internal data class SongFM(
    @field:Json(name = "Song Clean")
    val name: String,

    @field:Json(name = "ARTIST CLEAN")
    val artist: String,

    @field:Json(name = "Release Year")
    val year: String
)