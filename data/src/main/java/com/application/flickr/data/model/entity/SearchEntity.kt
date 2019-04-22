package com.application.flickr.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Harsh Jain on 19/04/19.
 */

@Entity
data class SearchEntity(
    var searchTerm: String,
    var timestamp: Long
) {
    init {
        searchTerm = searchTerm.toLowerCase() //Assuming search terms are saved as lowercase only
    }

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}