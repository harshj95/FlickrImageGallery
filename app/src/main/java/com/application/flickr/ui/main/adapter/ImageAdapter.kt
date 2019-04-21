package com.application.flickr.ui.main.adapter

import android.view.View
import com.application.flickr.R
import com.application.flickr.data.model.Image
import com.application.flickr.data.model.entity.UrlEntity
import com.application.flickr.ui.common.SingleViewAdapter

/**
 * Created by Harsh Jain on 18/04/19.
 */

class ImageAdapter(var images: List<UrlEntity>) :
    SingleViewAdapter<UrlEntity, ImageViewHolder>() {

    override fun getItemId(position: Int): Long = images[position].hashCode().toLong()

    override fun getItem(position: Int): UrlEntity = images[position]

    override fun getViewHolder(view: View): ImageViewHolder = ImageViewHolder(view)

    override fun getItemCount(): Int = images.size

    override fun getItemViewType(position: Int): Int = R.layout.item_home_image
}