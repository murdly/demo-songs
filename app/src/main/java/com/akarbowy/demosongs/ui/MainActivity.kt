package com.akarbowy.demosongs.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akarbowy.demosongs.R
import com.akarbowy.songs.ui.SongsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { navigateToSongs() }
    }

    private fun navigateToSongs() {
        startActivity(Intent(this, SongsActivity::class.java))
    }
}
