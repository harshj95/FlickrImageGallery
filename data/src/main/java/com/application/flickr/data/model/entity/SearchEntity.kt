package com.application.flickr.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Harsh Jain on 19/04/19.
 */

@Entity
data class SearchEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var searchTerm: String,
    var urls: List<String>
) {
    init {
        searchTerm = searchTerm.toLowerCase() //Assuming search terms are saved as lowercase only
    }
}