package com.akarbowy.songs.data.sources.file

import com.akarbowy.songs.data.sources.LocalDataSource
import com.akarbowy.utils.resources.ResourceProvider
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
internal object FileModule {

    @JvmStatic
    @Provides
    @Singleton
    fun provideSongsJsonAdapter(): JsonAdapter<List<SongFM>> {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, SongFM::class.java)

        return moshi.adapter<List<SongFM>>(type)
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideSongFileProvider(
        resourceProvider: ResourceProvider,
        adapter: JsonAdapter<List<SongFM>>
    ) = SongFileProvider(resourceProvider, adapter)

    @Provides
    @JvmStatic
    @Singleton
    fun provideLocalDataSource(songFileProvider: SongFileProvider): LocalDataSource = songFileProvider

}