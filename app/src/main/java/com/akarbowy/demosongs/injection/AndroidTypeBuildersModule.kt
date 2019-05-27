package com.akarbowy.demosongs.injection

import com.akarbowy.demosongs.MainActivity
import com.akarbowy.songs.data.DataModule
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module
abstract class AndroidTypeBuildersModule {

    @Singleton
    @ContributesAndroidInjector(modules = [DataModule::class])
    abstract fun bindActivity(): MainActivity
}

