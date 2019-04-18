package com.application.flickr.config.di

import com.application.flickr.api.NetworkApi
import com.application.flickr.api.RetrofitProvider
import com.application.flickr.config.AppExecutors
import com.application.flickr.config.AppInitializer
import com.application.flickr.config.AppInitializers
import com.application.flickr.config.log.Logger
import com.application.flickr.config.log.TimberInitializer
import com.application.flickr.config.log.TimberLogger
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
 * Created by Harsh Jain on 18/04/19.
 */

val appModule = Kodein.Module("appModule") {

    bind<AppExecutors>() with singleton {
        AppExecutors()
    }

    bind<NetworkApi>() with singleton {
        RetrofitProvider.provideDefaultRetrofit(instance(), true).create(NetworkApi::class.java)
    }

//    bind<AppDatabase>() with eagerSingleton {
//        AppDatabase.getInstance(instance())
//    }
//
//    bind<TodoDao>() with singleton {
//        instance<AppDatabase>().todoDao()
//    }
//
//    bind<TodoRepository>() with singleton {
//        TodoRepository(instance(), instance(), instance())
//    }

    bind<Logger>() with singleton { TimberLogger() }

    bind<AppInitializer>("TimberInitializer") with singleton {
        TimberInitializer(instance())
    }

    bind<AppInitializers>() with singleton {
        AppInitializers()
    }
}