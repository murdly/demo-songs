package com.akarbowy.songs.data

import com.akarbowy.domain.models.SongDM
import com.akarbowy.songs.data.sources.file.SongFM
import com.akarbowy.songs.data.sources.network.SongAM
import com.akarbowy.utils.date.DateHelper


@JvmName("mapAMtoDM")
internal fun List<SongAM>.toDM() = map { it.toDM() }

private fun SongAM.toDM() = SongDM(this.name, this.artist, DateHelper.parseYear(year))

@JvmName("mapFMtoDM")
internal fun List<SongFM>.toDM() = map { it.toDM() }

private fun SongFM.toDM() = SongDM(this.name, this.artist, this.year.toIntOrNull())