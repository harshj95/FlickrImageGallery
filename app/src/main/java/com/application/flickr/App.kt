package com.application.flickr

import android.app.Application
import com.application.flickr.config.AppInitializers
import com.application.flickr.config.di.appModule
import com.application.flickr.data.di.dbModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidCoreModule
import org.kodein.di.generic.instance

/**
 * Created by Harsh Jain on 18/04/19.
 */

class App : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidCoreModule(this@App))
        import(appModule)
        import(dbModule)
    }

    private val initializers: AppInitializers by kodein.instance()

    override fun onCreate() {
        super.onCreate()
        kodeinInstance = kodein

        initializers.init(this)
    }

    companion object {
        internal lateinit var kodeinInstance: Kodein
    }
}