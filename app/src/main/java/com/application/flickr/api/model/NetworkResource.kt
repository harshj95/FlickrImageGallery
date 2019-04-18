package com.application.flickr.api.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import com.application.flickr.config.AppExecutors
import java.util.*

/**
 * Created by Harsh Jain on 18/04/19.
 */

abstract class NetworkResource<ResultType, RequestType> @MainThread
internal constructor(private val appExecutors: AppExecutors) {

    val result = MediatorLiveData<Resource<ResultType>>()

    @MainThread
    fun setValue(newValue: Resource<ResultType>) {
        if (!Objects.equals(result.value, newValue)) {
            result.value = newValue
        }
    }

    open fun onFetchFailed() {}

    fun asLiveData(): LiveData<Resource<ResultType>> = result
}