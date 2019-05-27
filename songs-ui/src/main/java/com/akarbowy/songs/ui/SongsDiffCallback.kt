package com.akarbowy.songs.ui

import androidx.recyclerview.widget.DiffUtil


internal class SongsDiffCallback : DiffUtil.ItemCallback<SongUIM>() {

    override fun areItemsTheSame(oldItem: SongUIM, newItem: SongUIM) = oldItem == newItem

    override fun areContentsTheSame(oldItem: SongUIM, newItem: SongUIM) = oldItem == newItem

}