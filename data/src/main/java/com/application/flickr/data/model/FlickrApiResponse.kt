package com.application.flickr.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Harsh Jain on 18/04/19.
 */

data class FlickrApiResponse(
    @SerializedName("photos")
    @Expose
    var data: ImagesMeta? = null,
    @SerializedName("stat")
    @Expose
    var status: String? = null
)