package com.application.flickr.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Harsh Jain on 18/04/19.
 */

data class Image(
    @SerializedName("id")
    @Expose
    private val id: String? = null,
    @SerializedName("owner")
    @Expose
    private val owner: String? = null,
    @SerializedName("secret")
    @Expose
    private val secret: String? = null,
    @SerializedName("server")
    @Expose
    private val server: String? = null,
    @SerializedName("farm")
    @Expose
    private val farm: String? = null,
    @SerializedName("title")
    @Expose
    private val title: String? = null,
    @SerializedName("url_m")
    @Expose
    private val url: String? = null,
    @SerializedName("height_m")
    @Expose
    private val height: String? = null,
    @SerializedName("width_m")
    @Expose
    private val width: String? = null
)