package com.application.flickr.util.extensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

/**
 * Created by Harsh Jain on 18/04/19.
 */

inline fun ImageView.loadImage(
    requestManager: RequestManager = Glide.with(this),
    func: RequestManager.() -> RequestBuilder<Drawable>
) {

    val requestOptions = requestManager.func().apply(
        RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE).override(18, 18).centerCrop()
    )

    requestOptions.into(this)
}

fun ImageView.clearImage(
    requestManager: RequestManager = Glide.with(this)
) {
    requestManager.clear(this)
}