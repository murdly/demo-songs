package com.akarbowy.demosongs.managers

import android.content.Context
import com.akarbowy.utils.resources.ResourceProvider
import java.io.InputStream


class AssetsManager(private val context: Context) : ResourceProvider {

    override fun openFile(name: String): InputStream {
        return context.assets.open(name)
    }
}