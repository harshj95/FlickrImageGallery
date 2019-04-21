package com.application.flickr.ui.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Harsh Jain on 18/04/19.
 */

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bindTo(t: T)
    abstract fun cleanUp()
}