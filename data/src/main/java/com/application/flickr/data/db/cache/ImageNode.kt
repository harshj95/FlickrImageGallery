package com.application.flickr.data.db.cache

/**
 * Created by Harsh Jain on 20/04/19.
 */

public class ImageNode(var key: String, var value: Boolean) {
    var prev: ImageNode? = null
    var next: ImageNode? = null
}