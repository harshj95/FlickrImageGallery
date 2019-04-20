package com.application.flickr.data.repo

import androidx.lifecycle.LiveData
import com.application.flickr.data.api.NetworkApi
import com.application.flickr.data.api.model.ApiResponse
import com.application.flickr.data.api.model.NetworkCacheResource
import com.application.flickr.data.api.model.Resource
import com.application.flickr.data.db.dao.ImageDao
import com.application.flickr.data.livedata.AbsentLiveData
import com.application.flickr.data.model.FlickrApiResponse
import com.application.flickr.data.model.Image
import com.application.flickr.data.util.AppExecutors
import com.application.flickr.data.util.C

/**
 * Created by Harsh Jain on 18/04/19.
 */

class ImageRepository(
    private val appExecutors: AppExecutors,
    private val networkApi: NetworkApi,
    private val imageDao: ImageDao
) : Repository() {

    fun getImages(searchTerm: String, page: Int): LiveData<Resource<List<Image>>> {
        return object : NetworkCacheResource<List<Image>, FlickrApiResponse>(appExecutors) {
            override fun parseDataIfRequired(requestType: FlickrApiResponse): List<Image> {
                return requestType.data?.images!!
            }

            override fun cacheCallResult(apiRequestResponse: FlickrApiResponse) {
//                imageDao.insertUrl(UrlEntity(1, "qw"))
            }

            override fun shouldFetch(data: List<Image>?): Boolean = true
//                rue for this call because we have pagination. Otherwise a limiter should be used (data or rate)

            override fun loadFromDb(): LiveData<List<Image>> {
                return AbsentLiveData.create<Resource<List<Image>>>() as LiveData<List<Image>>
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