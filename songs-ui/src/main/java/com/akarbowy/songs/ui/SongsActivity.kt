package com.akarbowy.songs.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.akarbowy.common.injection.ViewModelFactory
import com.akarbowy.songs.ui.SongsViewModel.Filter
import com.akarbowy.songs.ui.SongsViewModel.State
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_songs.*
import kotlinx.android.synthetic.main.view_error.*
import javax.inject.Inject

class SongsActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var filterPopup: PopupMenu

    private lateinit var adapter: SongsAdapter

    private lateinit var viewModel: SongsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_songs)

        initUi()
    }

    private fun initUi() {
        setUpFilter()
        setupList()

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(SongsViewModel::class.java)

        retry.setOnClickListener { viewModel.retry() }

        viewModel.filter.observe(this, Observer { updateFilter(it) })

        viewModel.state.observe(this, Observer { updateUI(it) })
    }

    private fun setUpFilter() {
        val anchor = findViewById<View>(R.id.icon)
            .apply { setOnClickListener { filterPopup.show() } }

        with(PopupMenu(this, anchor)) {
            filterPopup = this
            inflate(R.menu.filter)

            setOnMenuItemClickListener { item ->
                val filter = when (item.itemId) {
                    R.id.remote -> Filter.Remote
                    R.id.local -> Filter.Local
                    else -> Filter.All
                }
                viewModel.setFilter(filter)
                true
            }
        }
    }

    private fun setupList() {
        adapter = SongsAdapter()
            .also { list.adapter = it }

        list.setHasFixedSize(true)
    }

    private fun updateFilter(filter: Filter) {
        val item = when (filter) {
            Filter.All -> filterPopup.menu.findItem(R.id.all)
            Filter.Remote -> filterPopup.menu.findItem(R.id.remote)
            Filter.Local -> filterPopup.menu.findItem(R.id.local)
        }

        val filterValue = findViewById<TextView>(R.id.value)
        filterValue.text = item.title
        item.isChecked = true
    }

    private fun updateUI(state: State?) {
        when (state) {
            is State.Loading -> showLoading()
            is State.Songs -> showSongs(state.uim)
            is State.Error -> showError()
            is State.Empty -> showEmpty()
        }
    }

    private fun showLoading() {
        loading.visibility = View.VISIBLE
        list.visibility = View.GONE
        error.visibility = View.GONE
    }

    private fun showSongs(uim: List<SongUIM>) {
        list.visibility = View.VISIBLE
        empty.visibility = View.GONE
        loading.visibility = View.GONE
        error.visibility = View.GONE

        adapter.submitList(uim)
    }

    private fun showError() {
        error.visibility = View.VISIBLE
        empty.visibility = View.GONE
        list.visibility = View.GONE
        loading.visibility = View.GONE
    }

    private fun showEmpty() {
        empty.visibility = View.VISIBLE
        error.visibility = View.GONE
        list.visibility = View.GONE
        loading.visibility = View.GONE
    }

}