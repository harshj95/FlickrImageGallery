package com.application.flickr.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Harsh Jain on 18/04/19.
 */

data class Image(
    @SerializedName("id")
    @Expose
    val id: String? = null,
    @SerializedName("owner")
    @Expose
    val owner: String? = null,
    @SerializedName("secret")
    @Expose
    val secret: String? = null,
    @SerializedName("server")
    @Expose
    val server: String? = null,
    @SerializedName("farm")
    @Expose
    val farm: String? = null,
    @SerializedName("title")
    @Expose
    val title: String? = null,
    @SerializedName("url_m")
    @Expose
    val url: String? = null,
    @SerializedName("height_m")
    @Expose
    val height: String? = null,
    @SerializedName("width_m")
    @Expose
    val width: String? = null
)