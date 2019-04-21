package com.application.flickr.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Harsh Jain on 21/04/19.
 */

@Entity
data class UrlEntity(
    val url: String, val searchTerm: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}