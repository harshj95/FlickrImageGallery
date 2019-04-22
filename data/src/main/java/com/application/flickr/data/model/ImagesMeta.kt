package com.application.flickr.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Harsh Jain on 18/04/19.
 */

data class ImagesMeta(
    @SerializedName("page")
    @Expose
    val page: Int = 0,
    @SerializedName("pages")
    @Expose
    val pages: Int = 0,
    @SerializedName("perpage")
    @Expose
    val perPage: Int = 0,
    @SerializedName("total")
    @Expose
    val total: String? = null,
    @SerializedName("photo")
    @Expose
    val images: List<Image>? = null
)