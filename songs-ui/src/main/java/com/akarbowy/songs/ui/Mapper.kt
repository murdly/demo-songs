package com.akarbowy.songs.ui

import com.akarbowy.domain.models.SongDM


internal fun List<SongDM>.toUIM() = map { it.toUIM() }

internal fun SongDM.toUIM() = SongUIM(name.capitalize(), artist, year?.let { "$year" })
