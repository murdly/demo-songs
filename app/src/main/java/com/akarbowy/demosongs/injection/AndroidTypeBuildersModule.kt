package com.akarbowy.demosongs.injection

import com.akarbowy.songs.ui.SongsBuilderModule
import dagger.Module

@Module(includes = [SongsBuilderModule::class])
abstract class AndroidTypeBuildersModule