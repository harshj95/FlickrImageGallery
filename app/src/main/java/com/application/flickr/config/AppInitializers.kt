package com.application.flickr.config

import android.app.Application
import com.application.flickr.App.Companion.kodeinInstance
import org.kodein.di.generic.instance

class AppInitializers {
    private val initializer: MutableSet<@JvmSuppressWildcards AppInitializer> = hashSetOf()

    init {
        val timber: AppInitializer by kodeinInstance.instance("TimberInitializer")
        val stetho: AppInitializer by kodeinInstance.instance("StethoInitializer")
        initializer.add(timber)
        initializer.add(stetho)
    }

    fun init(application: Application) {
        initializer.forEach {
            it.init(application)
        }
    }
}
