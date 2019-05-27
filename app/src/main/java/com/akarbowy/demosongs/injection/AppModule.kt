package com.akarbowy.demosongs.injection

import android.content.Context
import com.akarbowy.common.injection.ViewModelModule
import com.akarbowy.demosongs.App
import com.akarbowy.demosongs.managers.AssetsManager
import com.akarbowy.utils.resources.ResourceProvider
import dagger.Module
import dagger.Provides


@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    fun provideContext(application: App): Context = application

    @Provides
    fun provideAssetsManager(context: Context): ResourceProvider =
        AssetsManager(context)

}