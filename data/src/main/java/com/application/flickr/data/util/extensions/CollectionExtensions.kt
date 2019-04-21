package com.application.flickr.data.util.extensions

import com.application.flickr.data.db.cache.ImageNode
import com.application.flickr.data.util.C.IMAGE_CACHE_SIZE
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by Harsh Jain on 20/04/19.
 */

fun <T> List<T>.convertToCacheList(incomingList: List<T>): List<T> {
    val toReturn = ArrayList<T>()
    if (this.size + incomingList.size > IMAGE_CACHE_SIZE) {
        for (i in incomingList.size until this.size - incomingList.size) {
            toReturn.add(this[i])
        }
        toReturn.addAll(incomingList)
    } else {
        toReturn.apply {
            addAll(this)
            addAll(incomingList)
        }
    }

    return toReturn
}

fun HashMap<String, ImageNode>.convertToString(): String {
    return Gson().toJson(this)
}

fun String.convertToHashMap(): HashMap<String, ImageNode> {
    val listType = object : TypeToken<HashMap<String, ImageNode>>() {
    }.type
    return Gson().fromJson(this, listType)
}

fun String.stringToStringList(): List<String>? {
    val listType = object : TypeToken<ArrayList<String>>() {

    }.type
    return Gson().fromJson<List<String>>(this, listType)
}