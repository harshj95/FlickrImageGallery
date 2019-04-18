package com.application.flickr.config

import android.app.Application
import com.application.flickr.App.Companion.kodeinInstance
import org.kodein.di.generic.instance

class AppInitializers {
    private val initializer: MutableSet<@JvmSuppressWildcards AppInitializer> = hashSetOf()

    init {
        val timber: AppInitializer by kodeinInstance.instance("TimberInitializer")
        initializer.add(timber)
    }

    fun init(application: Application) {
        initializer.forEach {
            it.init(application)
        }
    }
}
