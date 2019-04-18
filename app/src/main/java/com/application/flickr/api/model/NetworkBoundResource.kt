package com.application.flickr.api.model

import android.arch.lifecycle.LiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import com.application.flickr.config.AppExecutors

/**
 * Created by Harsh Jain on 18/04/19.
 */

abstract class NetworkBoundResource<ResultType, RequestType> @MainThread
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
        lateinit var apiResponse: LiveData<ApiResponse<RequestType>>
        appExecutors.networkIO().execute {
            apiResponse = createCall()
        }
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) { newData ->
            setValue(Resource.loading(newData))
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            if (response?.isSuccessful!!) {
                appExecutors.diskIO().execute {
                    saveCallResult(processResponse(response)!!)
                    appExecutors.mainThread().execute {
                        result.addSource(loadFromDb()) { newData ->
                            setValue(Resource.success(newData))
                        }
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
    protected abstract fun saveCallResult(entity: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}