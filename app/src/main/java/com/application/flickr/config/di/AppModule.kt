package com.application.flickr.config.di

import com.application.flickr.config.AppInitializer
import com.application.flickr.config.AppInitializers
import com.application.flickr.config.StethoInitializer
import com.application.flickr.config.log.Logger
import com.application.flickr.config.log.TimberInitializer
import com.application.flickr.config.log.TimberLogger
import com.application.flickr.data.api.NetworkApi
import com.application.flickr.data.api.RetrofitProvider
import com.application.flickr.data.db.ImageDatabase
import com.application.flickr.data.db.cache.LruCache
import com.application.flickr.data.db.dao.ImageDao
import com.application.flickr.data.repo.ImageRepository
import com.application.flickr.data.util.AppExecutors
import com.application.flickr.util.C
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.eagerSingleton
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
 * Created by Harsh Jain on 18/04/19.
 */

val appModule = Kodein.Module("appModule") {

    bind<Logger>() with singleton { TimberLogger() }

    bind<AppInitializer>("TimberInitializer") with singleton {
        TimberInitializer(instance())
    }

    bind<AppInitializer>("StethoInitializer") with singleton {
        StethoInitializer()
    }

    bind<AppInitializers>() with singleton {
        AppInitializers()
    }
}