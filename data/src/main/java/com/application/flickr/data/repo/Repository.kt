package com.application.flickr.data.repo

import com.application.flickr.data.api.RetrofitProvider

/**
 * Created by Harsh Jain on 19/04/19.
 */

abstract class Repository {
    internal fun imageListUrl() = RetrofitProvider.BASE_URL + "rest/"
}