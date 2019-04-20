package com.application.flickr.data.util.extensions

import com.application.flickr.data.util.C.IMAGE_CACHE_SIZE

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