package com.akarbowy.songs.ui

import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module
abstract class SongsBuilderModule {

    @Singleton
    @ContributesAndroidInjector(modules = [SongsModule::class])
    abstract fun bindSongsActivity(): SongsActivity

}