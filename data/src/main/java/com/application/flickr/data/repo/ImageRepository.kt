package com.application.flickr.data.repo

import androidx.lifecycle.LiveData
import com.application.flickr.data.api.NetworkApi
import com.application.flickr.data.api.model.ApiResponse
import com.application.flickr.data.api.model.NetworkCacheResource
import com.application.flickr.data.api.model.Resource
import com.application.flickr.data.db.cache.LruCache
import com.application.flickr.data.db.dao.ImageDao
import com.application.flickr.data.model.FlickrApiResponse
import com.application.flickr.data.model.entity.UrlEntity
import com.application.flickr.data.util.AppExecutors
import com.application.flickr.data.util.C

/**
 * Created by Harsh Jain on 18/04/19.
 */

class ImageRepository(
    private val appExecutors: AppExecutors,
    private val networkApi: NetworkApi,
    private val imageDao: ImageDao,
    private val lruCache: LruCache
) : Repository() {

    fun getImages(searchTerm: String, page: Int, isConnected: Boolean): LiveData<Resource<List<UrlEntity>>> {
        return object : NetworkCacheResource<List<UrlEntity>, FlickrApiResponse>(appExecutors) {
            override fun parseDataIfRequired(requestType: FlickrApiResponse): List<UrlEntity> {

                requestType.data!!.images!!.let {
                    val toReturn = ArrayList<UrlEntity>()

                    for (i in 0 until it.size) {
                        toReturn.add(UrlEntity(it[i].url!!, searchTerm))
                    }

                    return toReturn
                }
            }

            override fun cacheCallResult(apiRequestResponse: FlickrApiResponse) {
                apiRequestResponse.data!!.images!!.let { images ->
                    val toReturn = ArrayList<String>()

                    for (i in 0 until images.size) {
                        toReturn.add(images[i].url!!)
                    }

                    lruCache.put(searchTerm, toReturn)
                }
            }

            override fun shouldFetch(data: List<UrlEntity>?): Boolean = isConnected

            override fun loadFromDb(): LiveData<List<UrlEntity>> {
                return imageDao.getUrlsBySearchTermLiveData(searchTerm)
            }

            override fun createCall(): LiveData<ApiResponse<FlickrApiResponse>> {
                return networkApi.getPhotos(
                    imageListUrl(),
                    C.METHOD,
                    C.API_KEY,
                    searchTerm,
                    page,
                    C.EXTRAS,
                    C.IMAGES_PER_CALL,
                    C.FORMAT,
                    C.NO_JSON_CALLBACK
                )
            }
        }.asLiveData()
    }
}