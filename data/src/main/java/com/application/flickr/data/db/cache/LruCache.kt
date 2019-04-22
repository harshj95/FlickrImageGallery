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
    dbPreferenceManager: DbPreferenceManager
) {

    private var map: CacheMap = CacheMap(dbPreferenceManager)
    private var head: ImageNode? = null
    private var tail: ImageNode? = null

    fun put(searchTerm: String) {
        if (map.containsKey(searchTerm)) {
            val t = map.get(searchTerm)
            t!!.value = true

            remove(t)
            setHead(t)
        } else {
            if (map.size >= cap) {
                map.remove(tail!!.key)
                remove(tail!!)
                processCacheRequest(DatabaseAction.DeleteFromCache(searchTerm))
            }

            val t = ImageNode(searchTerm, true)
            setHead(t)
            map.put(searchTerm, t)
            processCacheRequest(DatabaseAction.AddToCache(searchTerm))
        }
    }

    fun putUrls(searchTerm: String, urls: List<String>) {
        appExecutors.diskIO().execute {
            urls.forEach {
                imageDao.insertUrlEntity(UrlEntity(it, searchTerm))
            }
        }
    }

    private fun processCacheRequest(action: DatabaseAction) {
        when (action) {
            is DatabaseAction.AddToCache -> {
                appExecutors.diskIO().execute {
                    imageDao.insertSearchEntity(SearchEntity(action.searchTerm))
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

    private fun remove(t: ImageNode) {
        if (t.prev != null) {
            t.prev!!.next = t.next
        } else {
            head = t.next
        }

        if (t.next != null) {
            t.next!!.prev = t.prev
        } else {
            tail = t.prev
        }
    }

    private fun setHead(t: ImageNode) {
        if (head != null) {
            head!!.prev = t
        }

        t.next = head
        t.prev = null
        head = t

        if (tail == null) {
            tail = head
        }
    }

    private sealed class DatabaseAction {
        class AddToCache(val searchTerm: String) : DatabaseAction()
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