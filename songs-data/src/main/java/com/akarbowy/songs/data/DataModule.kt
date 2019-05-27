package com.akarbowy.songs.data

import com.akarbowy.domain.repositories.SongsRepository
import com.akarbowy.songs.data.sources.LocalDataSource
import com.akarbowy.songs.data.sources.RemoteDataSource
import com.akarbowy.songs.data.sources.file.FileModule
import com.akarbowy.songs.data.sources.network.NetworkModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
        NetworkModule::class,
        FileModule::class
    ]
)
object DataModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideSongsRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): SongsRepository = SongsRepositoryImpl(localDataSource, remoteDataSource)

}