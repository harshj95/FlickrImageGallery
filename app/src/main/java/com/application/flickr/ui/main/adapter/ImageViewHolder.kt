package com.application.flickr.ui.main.adapter

import android.view.View
import com.application.flickr.data.model.entity.UrlEntity
import com.application.flickr.ui.common.BaseViewHolder
import com.application.flickr.util.extensions.clearImage
import com.application.flickr.util.extensions.loadImage
import kotlinx.android.synthetic.main.item_home_image.view.*

/**
 * Created by Harsh Jain on 18/04/19.
 */

class ImageViewHolder(itemView: View) : BaseViewHolder<UrlEntity>(itemView) {

    override fun cleanUp() {
        itemView.apply {
            iv_image.clearImage()
        }
    }

    override fun bindTo(t: UrlEntity?) {
        t?.let {
            itemView.apply {
                iv_image.loadImage {
                    load(t.url)
                }
            }
        }
    }
}