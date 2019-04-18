package com.application.flickr.data.livedata

import android.arch.lifecycle.MediatorLiveData

/**
 * Created by Harsh Jain on 18/03/19.
 */

open class WiseLiveData<T> : MediatorLiveData<T>() {
    private var cleanup: (() -> Unit)? = null

    fun dispatchSuccess(resource: T) {
        value = resource
    }

    fun dispatchLoading(resource: T) {
        value = resource
    }

    fun dispatchError(resource: T) {
        value = resource
    }

    init {
        postValue(null)
    }

    companion object {
        fun <T> create(): WiseLiveData<T> {
            return WiseLiveData()
        }
    }
}