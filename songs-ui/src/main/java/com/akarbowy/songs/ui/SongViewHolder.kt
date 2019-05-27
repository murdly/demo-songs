package com.akarbowy.songs.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.viewholder_song.view.*


internal class SongViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun onBind(item: SongUIM) {
        with(SongUIMDecorator(itemView.context, item)) {
            itemView.title.text = title
            itemView.subtitle.text = subtitle
        }
    }
}