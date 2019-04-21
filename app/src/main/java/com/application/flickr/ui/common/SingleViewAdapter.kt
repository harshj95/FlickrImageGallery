package com.application.flickr.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Harsh Jain on 18/04/19.
 */

abstract class SingleViewAdapter<T, VH : BaseViewHolder<T>> :
    RecyclerView.Adapter<VH>() {

    abstract fun getViewHolder(view: View): VH

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return getViewHolder(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onViewRecycled(holder: VH) {
        super.onViewRecycled(holder)
        holder.cleanUp()
    }

    abstract fun getItem(position: Int): T
}