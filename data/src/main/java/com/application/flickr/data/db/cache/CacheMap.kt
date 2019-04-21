package com.application.flickr.data.db.cache

import com.application.flickr.data.prefs.DbPreferenceManager
import com.application.flickr.data.prefs.DbPreferenceManager.Companion.CACHE_MAP
import com.application.flickr.data.util.extensions.convertToHashMap
import com.application.flickr.data.util.extensions.convertToString

/**
 * Created by Harsh Jain on 21/04/19.
 */

class CacheMap(private val dbPreferenceManager: DbPreferenceManager) : HashMap<String, ImageNode>() {

    init {
        if (dbPreferenceManager.getString(CACHE_MAP, "").isEmpty()) {
            dbPreferenceManager.putString(CACHE_MAP, HashMap<String, ImageNode>().convertToString())
        }
    }

    override fun containsKey(key: String): Boolean {
        return dbPreferenceManager.getString(CACHE_MAP, "").convertToHashMap().containsKey(key)
    }

    override fun get(key: String): ImageNode? {
        return dbPreferenceManager.getString(CACHE_MAP, "").convertToHashMap()[key]
    }

    override fun put(key: String, value: ImageNode): ImageNode? {
        val map = dbPreferenceManager.getString(CACHE_MAP, "").convertToHashMap()
        val toReturn = map.put(key, value)
        dbPreferenceManager.putString(CACHE_MAP, map.convertToString())
        return toReturn
    }

    override fun remove(key: String): ImageNode? {
        val map = dbPreferenceManager.getString(CACHE_MAP, "").convertToHashMap()
        val toReturn = map.remove(key)
        dbPreferenceManager.putString(CACHE_MAP, map.convertToString())
        return toReturn
    }

    override val size: Int
        get() = dbPreferenceManager.getString(CACHE_MAP, "").convertToHashMap().size
}