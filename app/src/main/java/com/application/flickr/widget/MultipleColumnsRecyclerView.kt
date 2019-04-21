package com.application.flickr.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Harsh Jain on 18/04/19.
 */

@Suppress("DEPRECATION")
class MultipleColumnsRecyclerView : RecyclerView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    private val doubleGridLayoutManager: GridLayoutManager
    private val tripleGridLayoutManager: GridLayoutManager
    private val quadrupleGridLayoutManager: GridLayoutManager

    init {
        this.setHasFixedSize(true)
        setItemViewCacheSize(30)
        isDrawingCacheEnabled = true
        drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        doubleGridLayoutManager = GridLayoutManager(this.context, 2)
        tripleGridLayoutManager = GridLayoutManager(this.context, 3)
        quadrupleGridLayoutManager = GridLayoutManager(this.context, 4)
        this.layoutManager = doubleGridLayoutManager
    }

    fun changeGrid(col: Int) {
        when (col) {
            2 -> layoutManager = doubleGridLayoutManager
            3 -> layoutManager = tripleGridLayoutManager
            4 -> layoutManager = quadrupleGridLayoutManager
        }
        requestLayout()
    }
}