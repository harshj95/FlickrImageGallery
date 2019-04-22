package com.application.flickr.data.di

import com.application.flickr.data.api.NetworkApi
import com.application.flickr.data.api.RetrofitProvider
import com.application.flickr.data.db.ImageDatabase
import com.application.flickr.data.db.cache.LruCache
import com.application.flickr.data.db.dao.ImageDao
import com.application.flickr.data.prefs.DbPreferenceManager
import com.application.flickr.data.repo.ImageRepository
import com.application.flickr.data.util.AppExecutors
import com.application.flickr.data.util.C
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.eagerSingleton
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
 * Created by Harsh Jain on 21/04/19.
 */

val dbModule = Kodein.Module("dbModule") {

    bind<AppExecutors>() with singleton {
        AppExecutors()
    }

    bind<NetworkApi>() with singleton {
        RetrofitProvider.provideDefaultRetrofit(instance(), true).create(NetworkApi::class.java)
    }

    bind<ImageDatabase>() with eagerSingleton {
        ImageDatabase.getInstance(instance())
    }

    bind<LruCache>() with singleton {
        LruCache.getInstance(instance(), C.SEARCH_TERM_CACHE_SIZE, instance(), instance())
    }

    bind<ImageRepository>() with singleton {
        ImageRepository(
            instance(),
            instance(),
            instance(),
            instance()
        )
    }

    bind<ImageDao>() with singleton {
        instance<ImageDatabase>().imageDao()
    }

    bind<DbPreferenceManager>() with singleton {
        DbPreferenceManager.getInstance(instance())
    }
}