package com.application.flickr.ui.main.adapter

import android.view.View
import com.application.flickr.R
import com.application.flickr.data.model.entity.UrlEntity
import com.application.flickr.ui.common.SingleViewAdapter

/**
 * Created by Harsh Jain on 18/04/19.
 */

class ImageAdapter :
    SingleViewAdapter<UrlEntity, ImageViewHolder>() {
    override fun areItemsTheSame(oldItem: UrlEntity, newItem: UrlEntity) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: UrlEntity, newItem: UrlEntity) = oldItem == newItem

    override fun getViewHolder(view: View): ImageViewHolder = ImageViewHolder(view)

    override fun getItemViewType(position: Int): Int = R.layout.item_home_image
}