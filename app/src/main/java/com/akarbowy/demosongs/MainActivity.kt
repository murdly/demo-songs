package com.akarbowy.demosongs

import android.os.Bundle
import com.akarbowy.domain.repositories.SongsRepository
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() { //todo revert to ACA

    @Inject
    lateinit var repo: SongsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repo.getAllSongs()
    }
}
