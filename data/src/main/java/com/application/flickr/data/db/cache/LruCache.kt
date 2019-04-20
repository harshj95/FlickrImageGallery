package com.application.flickr.data.db.cache

import com.application.flickr.data.db.dao.ImageDao
import com.application.flickr.data.model.entity.SearchEntity
import com.application.flickr.data.util.AppExecutors
import com.application.flickr.data.util.extensions.convertToCacheList


/**
 * Created by Harsh Jain on 20/04/19.
 */

class LruCache(private val imageDao: ImageDao, private val cap: Int, private val appExecutors: AppExecutors) {

    private var map: HashMap<String, ImageNode> = HashMap()
    private var head: ImageNode? = null
    private var tail: ImageNode? = null

    fun put(searchTerm: String, urls: List<String>) {
        if (map.containsKey(searchTerm)) {
            val t = map[searchTerm]
            t!!.value = true

            remove(t)
            setHead(t)
            processCacheRequest(DatabaseAction.UpdateCache(searchTerm, urls))
        } else {
            if (map.size >= cap) {
                map.remove(tail!!.key)
                remove(tail!!)
                processCacheRequest(DatabaseAction.DeleteFromCache(searchTerm, urls))
            }

            val t = ImageNode(searchTerm, true)
            setHead(t)
            map[searchTerm] = t
            processCacheRequest(DatabaseAction.AddToCache(searchTerm, urls))
        }
    }

    private fun processCacheRequest(action: DatabaseAction) {
        when (action) {
            is DatabaseAction.AddToCache -> {
                appExecutors.diskIO().execute {
                    imageDao.insertSearchEntity(SearchEntity(action.searchTerm, action.urls))
                }
            }

            is DatabaseAction.UpdateCache -> {
                appExecutors.diskIO().execute {
                    val savedUrls = imageDao.getUrlsBySearchTerm(action.searchTerm)
                    imageDao.updateUrlsForSearchTerm(action.searchTerm, savedUrls.convertToCacheList(action.urls))
                }
            }

            is DatabaseAction.DeleteFromCache -> {
                appExecutors.diskIO().execute {
                    imageDao.deleteEntityBySearchTerm(action.searchTerm)
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
        class AddToCache(val searchTerm: String, val urls: List<String>) : DatabaseAction()
        class UpdateCache(val searchTerm: String, val urls: List<String>) : DatabaseAction()
        class DeleteFromCache(val searchTerm: String, val urls: List<String>) : DatabaseAction()
    }
}