package com.akarbowy.songs.ui

import androidx.lifecycle.ViewModel
import com.akarbowy.common.injection.ViewModelKey
import com.akarbowy.domain.repositories.SongsRepository
import com.akarbowy.domain.usecases.GetAllSongsUseCase
import com.akarbowy.domain.usecases.GetLocalSongsUseCase
import com.akarbowy.domain.usecases.GetRemoteSongsUseCase
import com.akarbowy.songs.data.DataModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap


@Module(includes = [DataModule::class])
internal abstract class SongsModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideGetAllSongsUseCase(songsRepository: SongsRepository): GetAllSongsUseCase =
            GetAllSongsUseCase(songsRepository)

        @Provides
        @JvmStatic
        fun provideGetLocalSongsUseCase(songsRepository: SongsRepository): GetLocalSongsUseCase =
            GetLocalSongsUseCase(songsRepository)

        @Provides
        @JvmStatic
        fun provideGetRemoteSongsUseCase(songsRepository: SongsRepository): GetRemoteSongsUseCase =
            GetRemoteSongsUseCase(songsRepository)
    }

    @Binds
    @IntoMap
    @ViewModelKey(SongsViewModel::class)
    abstract fun bindSongsViewModel(viewModel: SongsViewModel): ViewModel

}