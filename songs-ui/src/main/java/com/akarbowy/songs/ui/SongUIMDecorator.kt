package com.akarbowy.songs.ui

import android.content.Context


internal class SongUIMDecorator(
    private val context: Context,
    uim: SongUIM
) {

    val title = uim.name

    val subtitle = uim.artist + uim.year?.let { context.getString(R.string.song_subtitle, it) }.orEmpty()

}