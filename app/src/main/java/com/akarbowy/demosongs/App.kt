package com.akarbowy.demosongs

import com.akarbowy.demosongs.injection.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<App> =
        DaggerAppComponent.builder()
            .application(this)
            .build()

}