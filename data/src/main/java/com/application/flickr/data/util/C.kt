package com.application.flickr.data.util

/**
 * Created by Harsh Jain on 18/04/19.
 */

object C {
    const val METHOD = "flickr.photos.search"
    const val API_KEY = "90887ad6c9d3e12a6c42b1b8143e9a41"
    const val EXTRAS = "url_m"
    const val FORMAT = "json"
    const val NO_JSON_CALLBACK = 1 //to get the callback as JSON


    //To maintain the grid multiples of (LCM of 2,3,4 = 12) are taken
    const val IMAGES_PER_CALL = 24
    const val IMAGE_CACHE_SIZE = 96
}