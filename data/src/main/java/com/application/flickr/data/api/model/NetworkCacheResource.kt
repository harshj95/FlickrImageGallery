package com.application.flickr.data.api.model

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.application.flickr.config.AppExecutors

/**
 * Created by Harsh Jain on 18/04/19.
 */

abstract class NetworkCacheResource<ResultType, RequestType> @MainThread
internal constructor(private val appExecutors: AppExecutors) : NetworkResource<ResultType, RequestType>(appExecutors) {

    init {
        setValue(Resource.loading(null))
        @Suppress("LeakingThis") val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    setValue(Resource.success(newData))
                }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) { newData ->
            setValue(Resource.loading(newData))
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            if (response?.isSuccessful!!) {
                processResponse(response)?.let {
                    setValue(Resource.success(parseDataIfRequired(it)))
                    appExecutors.diskIO().execute {
                        cacheCallResult(it)
                    }
                }

            } else {
                onFetchFailed()
                result.addSource(dbSource) { newData ->
                    setValue(Resource.error(response.errorMessage, newData))
                }
            }
        }
    }

    @WorkerThread
    private fun processResponse(response: ApiResponse<RequestType>?): RequestType? {
        return response?.body
    }

    @WorkerThread
    protected abstract fun cacheCallResult(apiRequestResponse: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    @MainThread
    protected abstract fun parseDataIfRequired(requestType: RequestType): ResultType
}