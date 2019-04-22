package com.application.flickr.data.db.cache

import com.application.flickr.data.db.dao.ImageDao
import com.application.flickr.data.model.entity.SearchEntity
import com.application.flickr.data.model.entity.UrlEntity
import com.application.flickr.data.prefs.DbPreferenceManager
import com.application.flickr.data.util.AppExecutors

/**
 * Created by Harsh Jain on 20/04/19.
 */

class LruCache private constructor(
    private val imageDao: ImageDao,
    private val cap: Int,
    private val appExecutors: AppExecutors,
    private val dbPreferenceManager: DbPreferenceManager
) {

    fun put(searchTerm: String, urls: List<String>) {
        appExecutors.diskIO().execute {
            val list = imageDao.getOrderedSearchEntities()

            if (list.isNullOrEmpty()) {
                processCacheRequest(DatabaseAction.AddToCache(searchTerm, urls))
            } else {
                if (dbPreferenceManager.currentCacheSize == cap) {
                    processCacheRequest(DatabaseAction.DeleteFromCache(list[0].searchTerm))
                    processCacheRequest(DatabaseAction.AddToCache(searchTerm, urls))
                } else {
                    var found: SearchEntity? = null

                    list.forEach {
                        if (it.searchTerm == searchTerm) {
                            it.timestamp = System.nanoTime()
                            found = it
                        }
                    }

                    if (found != null) {
                        processCacheRequest(DatabaseAction.UpdateCache(found!!, urls))
                    } else {
                        val currentCacheSize = dbPreferenceManager.currentCacheSize
                        dbPreferenceManager.currentCacheSize = currentCacheSize + 1
                        processCacheRequest(DatabaseAction.AddToCache(searchTerm, urls))
                    }
                }
            }
        }
    }

    private fun processCacheRequest(action: DatabaseAction) {
        when (action) {
            is DatabaseAction.AddToCache -> {
                appExecutors.diskIO().execute {
                    imageDao.insertSearchEntity(SearchEntity(action.searchTerm, System.nanoTime()))
                    action.urls.forEach {
                        imageDao.insertUrlEntity(UrlEntity(it, action.searchTerm))
                    }
                }
            }

            is DatabaseAction.UpdateCache -> {
                appExecutors.diskIO().execute {
                    imageDao.updateSearchEntity(action.searchEntity)
                    action.urls.forEach {
                        imageDao.insertUrlEntity(UrlEntity(it, action.searchEntity.searchTerm))
                    }
                }
            }

            is DatabaseAction.DeleteFromCache -> {
                appExecutors.diskIO().execute {
                    imageDao.deleteEntityBySearchTerm(action.searchTerm)
                    imageDao.deleteUrlsBySearchTerm(action.searchTerm)
                }
            }
        }
    }

    private sealed class DatabaseAction {
        class AddToCache(val searchTerm: String, val urls: List<String>) : DatabaseAction()
        class UpdateCache(val searchEntity: SearchEntity, val urls: List<String>) : DatabaseAction()
        class DeleteFromCache(val searchTerm: String) : DatabaseAction()
    }

    companion object {
        @Volatile
        private var instance: LruCache? = null

        fun getInstance(
            imageDao: ImageDao,
            cap: Int,
            appExecutors: AppExecutors,
            dbPreferenceManager: DbPreferenceManager
        ): LruCache {
            val i = instance
            if (i != null) {
                return i
            }

            return synchronized(this) {
                val i2 = instance
                if (i2 != null) {
                    i2
                } else {
                    val created = LruCache(imageDao, cap, appExecutors, dbPreferenceManager)
                    instance = created
                    created
                }
            }
        }
    }
}