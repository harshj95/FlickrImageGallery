package com.application.flickr.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Harsh Jain on 18/04/19.
 */

data class ImagesMeta(
    @SerializedName("page")
    @Expose
    var page: Int = 0,
    @SerializedName("pages")
    @Expose
    var pages: Int = 0,
    @SerializedName("perpage")
    @Expose
    var perPage: Int = 0,
    @SerializedName("total")
    @Expose
    var total: String? = null,
    @SerializedName("photo")
    @Expose
    var images: List<Image>? = null
)