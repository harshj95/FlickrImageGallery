package com.application.flickr.api

import android.arch.lifecycle.LiveData
import com.application.flickr.api.model.ApiResponse
import com.application.flickr.data.model.FlickrApiResponse
import retrofit2.http.*


/**
 * Created by Harsh Jain on 18/04/19.
 */

interface NetworkApi {

    @POST
    fun getPhotos(
        @Url url: String,
        @Query("method") method: String,
        @Query("api_key") apiKey: String,
        @Query("text") text: String,
        @Query("page") page: Int,
        @Query("extras") extras: String,
        @Query("per_page") perPage: Int,
        @Query("format") format: String,
        @Query("nojsoncallback") noJsonCallback: Int
    ): LiveData<ApiResponse<FlickrApiResponse>>
}